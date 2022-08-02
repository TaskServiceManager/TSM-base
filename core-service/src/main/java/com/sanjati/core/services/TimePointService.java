package com.sanjati.core.services;


import com.sanjati.api.exceptions.OperationError;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.Task;
import com.sanjati.core.entities.TimePoint;
import com.sanjati.core.enums.TaskStatus;
import com.sanjati.core.enums.TimePointStatus;
import com.sanjati.core.repositories.TimePointRepository;
import com.sanjati.core.repositories.specifications.TimePointsSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
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
public class TimePointService {
    private final TimePointRepository timePointRepository;
    private final TaskService taskService;

    public TimePointService(TimePointRepository timePointRepository, @Lazy TaskService taskService) {
        this.timePointRepository = timePointRepository;
        this.taskService = taskService;
    }

    @Transactional
    public void changeStatusOrCreateTimePoint(Long taskId, Long userId, Long timePointId) {

        TimePoint tp;
        if (timePointId != null) {
            tp = timePointRepository.findById(timePointId).orElseThrow(()->new ResourceNotFoundException("Отметка не существует"));
            if (tp.getStatus() == TimePointStatus.FINISHED){
                throw new OperationError("Временная отметка уже закрыта");
            }
            tp.setStatus(TimePointStatus.FINISHED);
        } else {
            if(timePointRepository.existsByExecutorIdAndStatus(userId,TimePointStatus.IN_PROCESS)) {
                throw new OperationError("Нельзя открыть новую отметку пока есть незавешённые");
            }
            if(TaskStatus.ASSIGNED==taskService.getStatusByTaskId(taskId)){
                taskService.changeStatus(taskId, TaskStatus.ACCEPTED);
            }
            tp = new TimePoint();
            tp.setStatus(TimePointStatus.IN_PROCESS);
            tp.setExecutorId(userId);
            tp.setTaskId(taskId);
            timePointRepository.save(tp);
        }
    }

    @Transactional
    public void closeTimePointByTaskAndExecutorId(Long taskId, Long executorId) {
        Optional<TimePoint> timePoint = timePointRepository.findByTaskIdAndExecutorIdAndStatus(taskId, executorId, TimePointStatus.IN_PROCESS);
        timePoint.ifPresent(tp -> {
            if(TimePointStatus.IN_PROCESS==tp.getStatus()) {
                tp.setStatus(TimePointStatus.FINISHED);
                tp.setFinishedAt(LocalDateTime.now());
            }
        });
    }

    @Transactional
    public void closeAllTimePointsByTask(Long taskId) {
        List<TimePoint> timePoints = timePointRepository.findByTaskIdAndStatus(taskId, TimePointStatus.IN_PROCESS);
        timePoints.forEach(tp-> {
            tp.setStatus(TimePointStatus.FINISHED);
            tp.setFinishedAt(LocalDateTime.now());
        });
    }

    public Page<TimePoint> getAllTimePointsBySpec(Long userId,Long taskId, Integer page, LocalDateTime from, LocalDateTime to){
        Specification<TimePoint> spec = Specification.where(null);

        if(taskId !=null){
            spec = spec.and(TimePointsSpecifications.taskIdEquals(taskId));
        }
        if(userId !=null){
            spec = spec.and(TimePointsSpecifications.executorIdEquals(userId));
        }
        if(from != null) {
            spec = spec.and(TimePointsSpecifications.timeGreaterOrEqualsThan(from));
        }
        if(to != null) {
            spec = spec.and(TimePointsSpecifications.timeLessOrEqualsThan(to));
        }

        return timePointRepository.findAll(spec, PageRequest.of(page - 1, 100));
    }

    public TimePoint getLastByUserId(Long userId) {
        return timePointRepository.findFirstByExecutorIdOrderByStartedAtDesc(userId).stream().findFirst().orElse(null);
    }
}
