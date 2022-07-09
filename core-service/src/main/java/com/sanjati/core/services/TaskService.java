package com.sanjati.core.services;



import com.sanjati.api.core.TaskDto;
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
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

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


    public Page<Task> getAssignedTaskByExecutorId(Long id, Integer page) {
        return taskRepository.getAllAssignedTasksByExecutorId(id, PageRequest.of(page - 1, 10));
    }

    public void createTask(String username, TaskDto taskCreateDto) {
        //реализовать
    }

    public void takeTask(Long orderId, Long id) {
    }

    public Page<Task> getIncomingTasks(Integer page) {
        //заглушка
        return Page.empty();
    }
}
