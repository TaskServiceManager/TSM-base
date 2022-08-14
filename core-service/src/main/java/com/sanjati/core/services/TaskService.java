package com.sanjati.core.services;


import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.core.AssignDtoRq;
import com.sanjati.api.core.SearchParamsTaskDtoRq;
import com.sanjati.api.core.TaskDtoRq;
import com.sanjati.api.exceptions.MandatoryCheckException;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.Task;
import com.sanjati.core.enums.TaskStatus;
import com.sanjati.core.exceptions.ChangeTaskStatusException;
import com.sanjati.core.repositories.TaskRepository;
import com.sanjati.core.repositories.specifications.TaskSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CommentService commentService;
    private final AuthService authService;
    private final TimePointService timePointService;


    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found ID : " + id));
    }


    @Transactional
    public void changeStatus(Long taskId, Long userId, TaskStatus newStatus, String comment) {
        Task task = findById(taskId);
        boolean timePointActive = timePointService.checkTimePointsStatusByTaskId(taskId);
        switch (newStatus) {
            case CREATED: {
                if (TaskStatus.CANCELLED == task.getStatus()) {
                    task.setStatus(newStatus);
                    break;
                }
                throw new ChangeTaskStatusException("Изменить статус на 'Создана' можно только из статуса 'Отменена'");
            }
            case CANCELLED: {
                if (TaskStatus.CREATED == task.getStatus()) {
                    task.setStatus(newStatus);
                    break;
                }
                throw new ChangeTaskStatusException("Отменить заявку можно только со статусом 'Создана'");
            }
            case ASSIGNED: {
                if (TaskStatus.CREATED == task.getStatus()) {
                    task.setStatus(newStatus);
                    break;
                }
                throw new ChangeTaskStatusException("Назначить заявку можно только из статуса 'Создана'");
            }
            case ACCEPTED: {
                if (TaskStatus.ASSIGNED == task.getStatus()) {
                    task.setStatus(newStatus);
                    break;
                }
                if (TaskStatus.DELAYED == task.getStatus() || TaskStatus.APPROVED == task.getStatus()) {
                    if (comment == null) {
                        throw new ChangeTaskStatusException("Не заполнен обязательный комментарий");
                    }
                    commentService.leaveComment(taskId, userId, comment);
                    task.setStatus(newStatus);
                    break;
                }
                throw new ChangeTaskStatusException("Изменить статус на 'В работе' можно только при статусе 'Назначена', 'Отложена' или 'Утверждается'");
            }
            case APPROVED: {
                if (TaskStatus.ACCEPTED == task.getStatus()) {
                    if (comment == null) {
                        throw new ChangeTaskStatusException("Не заполнен обязательный комментарий");
                    }
                    commentService.leaveComment(taskId, userId, comment);
                    timePointService.closeAllTimePointsByTask(taskId);
                    task.setStatus(newStatus);
                    break;
                }
                throw new ChangeTaskStatusException("Утвердить заявку можно только из статуса 'В работе'");
            }
            case DELAYED: {
                if (TaskStatus.ACCEPTED == task.getStatus()) {
                    if (!timePointActive) {
                        commentService.leaveComment(taskId, task.getChiefId(), "заявка отложена");
                        task.setStatus(newStatus);
                        break;
                    }
                    throw new ChangeTaskStatusException("Изменить статус на 'Отложена' можно, когда все исполнители завершат работу");
                }
                throw new ChangeTaskStatusException("Отложить заявку можно только из статуса 'В работе'");
            }
            case COMPLETED: {
                if (TaskStatus.APPROVED == task.getStatus()) {
                    if (!timePointActive) {
                        if (comment == null) {
                            throw new ChangeTaskStatusException("Не заполнен обязательный комментарий");
                        }
                        commentService.leaveComment(taskId, userId, comment);
                        task.setStatus(newStatus);
                      task.setCompletedAt(LocalDateTime.now());
                      break;
                    }
                    throw new ChangeTaskStatusException("Изменить статус на 'Выполнена' можно, когда все исполнители завершат работу");

                }
                throw new ChangeTaskStatusException("Завершить можно только подтвержденную заявку");
            }
        }
    }

    @Transactional
    public void assignTask(Long taskId, Long assignerId, Long executorId) {
        Task task = getTaskAvailableForChanges(taskId);
        //id того, на кого будет назначаться заявка
        Long actualId = (executorId == null) ? assignerId : executorId;
        UserLightDto executor = authService.getUserLightById(actualId);//проверяет есть ли исполнитель с указанным id
        if (task.getStatus() == TaskStatus.CREATED) task.setStatus(TaskStatus.ASSIGNED);
        task.getExecutors().add(actualId);
        if (task.getExecutors().size() == 1) {
            task.setChiefId(actualId);
        }
        commentService.leaveComment(taskId, assignerId, executor.getShortNameFormatted() + " назначен в качестве исполнителя");
    }

    @Transactional
    public void assignTaskBatch(Long taskId, Long assignerId, AssignDtoRq assignDtoRq) {
        if (assignDtoRq.getExecutorIds() == null || assignDtoRq.getChiefId() == null) {
            throw new MandatoryCheckException("Не выбраны хотя бы один исполнитель и ответственный по заявке");
        }
        Task task = getTaskAvailableForChanges(taskId);
        Set<Long> taskExecutors = task.getExecutors();
        if (taskExecutors.size() > 0) {
            Set<Long> taskExecutorsCurrent = new HashSet<>(taskExecutors);
            taskExecutorsCurrent.forEach(e -> {
                //если удалили исполнителя, то нужно закрыть его открытый таймпоинт в этой заявке
                if(!assignDtoRq.getExecutorIds().contains(e)) {
                    timePointService.closeTimePointByTaskAndExecutorId(taskId, e);
                    taskExecutors.remove(e);
                }
            });
        }
        List<UserLightDto> executors = assignDtoRq.getExecutorIds().stream()
                .map(authService::getUserLightById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        executors.forEach(ex -> taskExecutors.add(ex.getId()));
        task.setChiefId(assignDtoRq.getChiefId());
        if (task.getStatus() == TaskStatus.CREATED) task.setStatus(TaskStatus.ASSIGNED);
        String appointed = executors.stream().map(UserLightDto::getShortNameFormatted).collect(Collectors.joining(", "));
        commentService.leaveComment(taskId, assignerId, appointed + (executors.size() > 1 ? " назначены в качестве исполнителей" : " назначен в качестве исполнителя"));
    }

    private Task getTaskAvailableForChanges(Long taskId) {
        List<TaskStatus> statuses = Arrays.stream(TaskStatus.values()).collect(Collectors.toList());
        statuses.remove(TaskStatus.CANCELLED);
        statuses.remove(TaskStatus.COMPLETED);
        return taskRepository.findByIdAndStatusIn(taskId,statuses).orElseThrow(() -> new ResourceNotFoundException("Task not found ID : " + taskId));
    }

    public Page<Task> findAllTasksBySpec(SearchParamsTaskDtoRq searchParams) {
        Specification<Task> spec = Specification.where(null);

        if(searchParams.getOwnerId() != null) {
            spec = spec.and(TaskSpecifications.ownerIdEquals(searchParams.getOwnerId()));
        }

        if (searchParams.getStatus() != null) {
            spec = spec.and(TaskSpecifications.statusEquals(TaskStatus.valueOf(searchParams.getStatus())));
        }

        if (searchParams.getFrom() != null) {
            spec = spec.and(TaskSpecifications.timeGreaterOrEqualsThan(searchParams.getFrom()));
        }

        if (searchParams.getTo() != null) {
            spec = spec.and(TaskSpecifications.timeLessThanOrEqualsThan(searchParams.getTo()));
        }

        if (searchParams.getExecutorId() != null) {
            spec = spec.and(TaskSpecifications.executorIdContainsIn(searchParams.getExecutorId()));
        }

        return this.taskRepository.findAll(spec, PageRequest.of(searchParams.getPage() - 1, 8, Sort.by(Sort.Direction.DESC, "createdAt")));
    }


    public void createTask(Long ownerId, TaskDtoRq taskCreateDto) {
        if(taskCreateDto.getTitle()==null || taskCreateDto.getDescription()==null) {
            throw new MandatoryCheckException("Не заполнены тема или описание заявки");
        }
        Task task = new Task();
        task.setOwnerId(ownerId);

        task.setTitle(taskCreateDto.getTitle());
        task.setDescription(taskCreateDto.getDescription());
        task.setStatus(TaskStatus.CREATED);
        taskRepository.save(task);
    }

    public boolean isUserTaskOwner(Long taskId, Long userId) {
        return taskRepository.existsByIdAndOwnerId(taskId, userId);
    }

    public TaskStatus getStatusByTaskId(Long taskId) {
        return taskRepository.findStatusByTaskId(taskId);
    }

    public void checkAccessToTask(String role, Long userId, Long taskId){
        if(!role.contains("EXECUTOR")){
            if(!isUserTaskOwner(userId, taskId)) throw new MandatoryCheckException("Нет доступа к чужим заявкам");
        }
    }

}
