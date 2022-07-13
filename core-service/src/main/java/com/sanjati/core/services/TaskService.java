package com.sanjati.core.services;



import com.sanjati.api.core.CreationTaskDto;
import com.sanjati.api.exceptions.ResourceNotFoundException;

import com.sanjati.core.entities.Task;
import com.sanjati.core.enums.TaskStatus;

import com.sanjati.core.repositories.TaskRepository;
import com.sanjati.core.repositories.specifications.TaskSpecifications;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final CommentService commentService;




    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public void changeStatus(Long id, String status){
        Task task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        task.setStatus(TaskStatus.valueOf(status));

    }

    @Transactional
    public void assignTask(Long taskId, Long executorId,Long appointedId) {
        //написать проверки

        Task task = findById(taskId).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        if (task.getStatus().equals(TaskStatus.CREATED))task.setStatus(TaskStatus.ASSIGNED);
        task.getExecutors().add(executorId);

        commentService.leaveComment(taskId,appointedId,">> назначил исполнителя >> ");


    }

    public Page<Task> findAllTasksBySpec(Long id,
                                         String from,
                                         String to,
                                         Integer page,
                                         String status) {
        Specification<Task> spec = Specification.where(null);

        if(id != null) {
            spec = spec.and(TaskSpecifications.idEquals(id));
        }

        if (status != null) {
            spec = spec.and(TaskSpecifications.statusEquals(status));
        }

        LocalDateTime newDateFormat;
        if (from != null) {
            newDateFormat = LocalDateTime.parse(from.substring(0, 22));
            spec = spec.and(TaskSpecifications.timeGreaterOrEqualsThan(newDateFormat));
            log.warn(from);
        }

        if (to != null) {
            newDateFormat = LocalDateTime.parse(to.substring(0, 22));
            log.warn(to);
            spec = spec.and(TaskSpecifications.timeLessThanOrEqualsThan(newDateFormat));
        }

        return this.taskRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }


    public void createTask(Long ownerId,CreationTaskDto taskCreateDto) {
        //TODO написать создание заявки
        Task task = new Task();
        task.setOwnerId(ownerId);

        task.setTitle(taskCreateDto.getTitle());
        task.setDescription(taskCreateDto.getDescription());
        task.setStatus(TaskStatus.CREATED);
        taskRepository.save(task);
    }





}
