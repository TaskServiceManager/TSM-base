package com.sanjati.core.services;



import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.Task;
import com.sanjati.core.enums.TaskStatus;
import com.sanjati.core.repositories.TaskRepository;
import com.sanjati.core.repositories.specifications.OrderSpecifications;
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

    private final TaskRepository ordersRepository;





    public List<Task> findOrdersByUsername(String username) {
        Specification<Task> spec = Specification.where(null);
        spec = spec.and(OrderSpecifications.usernameEquals(username));
        return ordersRepository.findAll(spec);
    }

    public Optional<Task> findById(Long id) {
        return ordersRepository.findById(id);
    }
    @Transactional
    public void changeStatus(Long id, String status){
        Task task = ordersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        task.setStatus(TaskStatus.valueOf(status));

    }

    @Transactional
    public void takeTask(Long orderId, Long executorId) {
        Task task = findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Task not found"));
        task.setStatus(TaskStatus.ASSIGNED);
        processService.createProcess(task, executorId);
    }

    public Page<Task> findOrdersById(Long id, String from, String to, Integer page) {
        Specification<Task> spec = Specification.where(null);
        spec = spec.and(OrderSpecifications.idEquals(id));

        LocalDateTime newDateFormat;
        if (from != null) {
            newDateFormat = LocalDateTime.parse(from.substring(0, 22));
            spec = spec.and(OrderSpecifications.timeGreaterOrEqualsThan(newDateFormat));
            log.warn(from);
        }

        if (to != null) {
            newDateFormat = LocalDateTime.parse(to.substring(0, 22));
            log.warn(to);
            spec = spec.and(OrderSpecifications.timeLessThanOrEqualsThan(newDateFormat));
        }

        return this.ordersRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }



}
