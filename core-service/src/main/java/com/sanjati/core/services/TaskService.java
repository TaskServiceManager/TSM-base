package com.sanjati.core.services;


import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.core.TaskDtoRq;
import com.sanjati.api.exceptions.ResourceNotFoundException;

import com.sanjati.core.entities.Task;
import com.sanjati.core.enums.TaskStatus;

import com.sanjati.core.exceptions.ChangeTaskStatusException;
import com.sanjati.core.integrations.AuthServiceIntegration;
import com.sanjati.core.repositories.TaskRepository;
import com.sanjati.core.repositories.specifications.TaskSpecifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CommentService commentService;
    private final AuthServiceIntegration authServiceIntegration;


    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Transactional
    public void changeStatus(Long id, String status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        TaskStatus newStatus = Arrays.stream(TaskStatus.values()).filter(st -> st.getRus().equals(status)).findFirst().
                orElseThrow(()-> new ResourceNotFoundException("Указанный статус заявки не найден"));
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
                if (TaskStatus.ASSIGNED == task.getStatus() ||
                        TaskStatus.DELAYED == task.getStatus() ||
                        TaskStatus.APPROVED == task.getStatus()) {
                    task.setStatus(newStatus);
                    break;
                }
                throw new ChangeTaskStatusException("Изменить статус на 'В работе' можно только при статусе 'Назначена', 'Отложена' или 'Утверждается'");
            }
            case APPROVED: {
                if (TaskStatus.ACCEPTED == task.getStatus()) {
                    task.setStatus(newStatus);
                    break;
                }
                throw new ChangeTaskStatusException("Утвердить заявку можно только из статуса 'В работе'");
            }
            case DELAYED: {
                if (TaskStatus.ACCEPTED == task.getStatus()) {
                    task.setStatus(newStatus);
                    break;
                }
                throw new ChangeTaskStatusException("Отложить заявку можно только из статуса 'В работе'");
            }
            case COMPLETED: {
                if (TaskStatus.APPROVED == task.getStatus()) {
                    task.setStatus(newStatus);
                    break;
                }
                throw new ChangeTaskStatusException("Завершить можно только подтвержденную заявку");
            }
        }

    }

    @Transactional
    public void assignTask(Long taskId, Long assignerId, Long executorId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (task.getStatus() == TaskStatus.CANCELLED || task.getStatus() == TaskStatus.COMPLETED) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Заявка отклонена или уже была выполнена.");
        }
        //id того, на кого будет назначаться заявка
        Long actualId = (executorId == null) ? assignerId : executorId;
        UserLightDto executor = authServiceIntegration.getUserLightById(actualId);//проверяет есть ли исполнитель с указанным id
        if (task.getStatus() == TaskStatus.CREATED) task.setStatus(TaskStatus.ASSIGNED);
        task.getExecutors().add(actualId);
        commentService.leaveComment(taskId, assignerId, executor.getShortNameFormatted() + " назначен в качестве исполнителя");
    }

    public Page<Task> findAllTasksBySpec(Long id,
                                         LocalDateTime from,
                                         LocalDateTime to,
                                         Integer page,
                                         String status,
                                         Long executorId) {
        Specification<Task> spec = Specification.where(null);

        if(id != null) {
            spec = spec.and(TaskSpecifications.ownerIdEquals(id));
        }

        if (status != null) {
            spec = spec.and(TaskSpecifications.statusEquals(Arrays.stream(TaskStatus.values()).filter(el-> status.equals(el.getRus())).findFirst().orElse(null)));
        }

        if (from != null) {
            spec = spec.and(TaskSpecifications.timeGreaterOrEqualsThan(from));
        }

        if (to != null) {
            spec = spec.and(TaskSpecifications.timeLessThanOrEqualsThan(to));
        }

        if (executorId != null) {
            spec = spec.and(TaskSpecifications.executorIdContainsIn(executorId));
        }

        return this.taskRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }


    public void createTask(Long ownerId, TaskDtoRq taskCreateDto) {
        //TODO написать создание заявки
        Task task = new Task();
        task.setOwnerId(ownerId);

        task.setTitle(taskCreateDto.getTitle());
        task.setDescription(taskCreateDto.getDescription());
        task.setStatus(TaskStatus.CREATED);
        taskRepository.save(task);
    }

    public boolean checkTaskOwnerId(Long userId, Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Задача не найдена"));
        return task.getOwnerId().equals(userId);
    }


}
