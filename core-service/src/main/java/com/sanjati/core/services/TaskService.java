package com.sanjati.core.services;



import com.sanjati.api.core.CreationTaskDto;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.Comment;
import com.sanjati.core.entities.Executor;
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
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ExecutorService executorService;
    private final CommentService commentService;


    public List<Task> findTaskByUsername(String username) {
        Specification<Task> spec = Specification.where(null);
        spec = spec.and(TaskSpecifications.usernameEquals(username));
        return taskRepository.findAll(spec);
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Transactional
    public void changeStatus(Long id, String status){
        Task task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        task.setStatus(TaskStatus.valueOf(status));

    }

    @Transactional
    public void takeTask(Long taskId, Long executorId,Long managerId) {
        Task task = findById(taskId).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        if (task.getStatus().equals(TaskStatus.CREATED))task.setStatus(TaskStatus.ASSIGNED);
        Executor executor = executorService.findExecutorByExecutorId(executorId);
        Executor manager;
        task.getExecutors().add(executor);
        if(executorId != managerId)
        {
            manager = executorService.findExecutorByExecutorId(managerId);
        }else {
            manager = executor;
        }
        commentService.leaveComment(manager,executor,">> назначил исполнителя >> ",task);
        taskRepository.save(task);

    }

    public Page<Task> findTasksByUserId(Long id, String from, String to, Integer page) {
        Specification<Task> spec = Specification.where(null);
        spec = spec.and(TaskSpecifications.idEquals(id));

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


    public void createTask(Long ownerId,String ownerName ,CreationTaskDto taskCreateDto) {
        //TODO написать создание заявки
        Task task = new Task();
        task.setOwnerId(ownerId);
        task.setOwnerName(ownerName);
        task.setTitle(taskCreateDto.getTitle());
        task.setDescription(taskCreateDto.getDescription());
        task.setStatus(TaskStatus.CREATED);
        taskRepository.save(task);
    }

    public Page<Task> getAllAssignedTasks(Long id, String from, String to, Integer page,String status) {//тут будут фильтры
        Executor executor = executorService.findExecutorByExecutorId(id);
        Specification<Task> spec = Specification.where(null);
        spec = spec.and(TaskSpecifications.executorsContains(executor));
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
        if (status != null) {

            spec = spec.and(TaskSpecifications.statusEquals(status));
        }

        return taskRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

    public Page<Task> getIncomingTasks(Integer page) {
        //заглушка
        return Page.empty();
    }


}
