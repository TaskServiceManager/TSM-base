package com.sanjati.core.services;



import com.sanjati.api.core.CreationTaskDtoRq;
import com.sanjati.api.exceptions.ResourceNotFoundException;

import com.sanjati.core.entities.Task;
import com.sanjati.core.enums.TaskStatus;

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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CommentService commentService;
    private final AuthServiceIntegration authServiceIntegration;


    public Task findById(Long id, String role, Long userId) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        if (role.contains("ROLE_EXECUTOR") || role.contains("ROLE_SENIOR")){
            return task;
        }
        if (!userId.equals(task.getOwnerId())){
            //TODO добавить кастомные исключения
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нет доступа к чужим заявкам");
        }
        return task;
    }

    @Transactional
    public void changeStatus(Long id, String status){
        Task task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        task.setStatus(TaskStatus.valueOf(status));

    }

    @Transactional
    public void assignTask(Long taskId, Long executorId, Long appointedId) {
        Task task = taskRepository.findById(taskId).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        if (task.getStatus().equals(TaskStatus.CANCELLED) || task.getStatus().equals(TaskStatus.COMPLETED)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Заявка отклонена или уже была выполнена.");
        }
        authServiceIntegration.getUserById(executorId);//проверяет есть ли исполнитель с указанным executorId
        if (task.getStatus().equals(TaskStatus.CREATED)) task.setStatus(TaskStatus.ASSIGNED);
        task.getExecutors().add(executorId);
        commentService.leaveComment(taskId,appointedId,">> назначил исполнителя >> ");
    }

    public Page<Task> findAllTasksBySpec(Long id,
                                         LocalDateTime from,
                                         LocalDateTime to,
                                         Integer page,
                                         String status) {
        Specification<Task> spec = Specification.where(null);

        if(id != null) {
            spec = spec.and(TaskSpecifications.idEquals(id));
        }

        if (status != null) {
            spec = spec.and(TaskSpecifications.statusEquals(status));
        }

        if (from != null) {
            spec = spec.and(TaskSpecifications.timeGreaterOrEqualsThan(from));
            log.warn(from.toString());
        }

        if (to != null) {
            log.warn(to.toString());
            spec = spec.and(TaskSpecifications.timeLessThanOrEqualsThan(to));
        }

        return this.taskRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }


    public void createTask(Long ownerId, CreationTaskDtoRq taskCreateDto) {
        //TODO написать создание заявки
        Task task = new Task();
        task.setOwnerId(ownerId);

        task.setTitle(taskCreateDto.getTitle());
        task.setDescription(taskCreateDto.getDescription());
        task.setStatus(TaskStatus.CREATED);
        taskRepository.save(task);
    }





}
