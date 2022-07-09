package com.sanjati.core.services;



import com.sanjati.api.core.CreationTaskDto;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.Executor;
import com.sanjati.core.entities.Task;
import com.sanjati.core.enums.TaskStatus;
import com.sanjati.core.repositories.ExecutorRepository;
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
    private final ExecutorRepository executorRepository;





    public List<Task> findOrdersByUsername(String username) {
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
    public void takeTask(Long taskId, Long executorId) {
        Task task = findById(taskId).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        task.setStatus(TaskStatus.ASSIGNED);
        Executor executor = executorRepository.findById(executorId).orElseThrow(()-> new ResourceNotFoundException("Executor not found"));
        task.getExecutors().add(executor);
        taskRepository.save(task);
        
    }

    public Page<Task> findOrdersByUserId(Long id, String from, String to, Integer page) {
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


    public void createTask(String username, CreationTaskDto taskCreateDto) {
    }

    public Page<Task> getAllAssignedTasks(Long id, String from, String to, Integer page,String status) {//тут будут фильтры
        Executor executor = executorRepository.getById(id);
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


        return taskRepository.
                findAll(spec,PageRequest.of(page - 1, 10));

    }
}
