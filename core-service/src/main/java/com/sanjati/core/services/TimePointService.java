package com.sanjati.core.services;


import com.sanjati.api.exceptions.OperationError;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.Task;
import com.sanjati.core.entities.TimePoint;
import com.sanjati.core.enums.TimePointStatus;
import com.sanjati.core.repositories.TimePointRepository;
import com.sanjati.core.repositories.specifications.TimePointsSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimePointService {
    private final TimePointRepository timePointRepository;
    private final TaskService taskService;
    @Transactional
    public void changeStatusOrCreateTimePoint(Long taskId, Long userId, Long timePointId) {
        TimePoint tp;
        if (timePointId != null) {
            tp = timePointRepository.findById(timePointId).orElseThrow(()->new ResourceNotFoundException("Отметка не существует"));
            if (tp.getStatus().equals(TimePointStatus.FINISHED)){
                throw new OperationError("Временная отметка уже закрыта");
            }
            tp.setStatus(TimePointStatus.FINISHED);
        }else {
            Specification<TimePoint> spec = Specification.where(null);
            spec = spec.and(TimePointsSpecifications.executorIdEquals(userId));
            spec = spec.and(TimePointsSpecifications.statusEquals(TimePointStatus.IN_PROCESS));

            if(!timePointRepository.findAll(spec).isEmpty()) {
                throw new OperationError("Нельзя открыть новую отметку пока есть незавешённые");
            }

            tp = new TimePoint();


            tp.setStatus(TimePointStatus.IN_PROCESS);
            tp.setExecutorId(userId);
            Task task = taskService.findById(taskId);
            tp.setTask(task);
            timePointRepository.save(tp);
        }


    }
    public Page<TimePoint> getAllTimePointsBySpec(Long userId,Long taskId, Integer page, LocalDateTime from, LocalDateTime to){
        Specification<TimePoint> spec = Specification.where(null);
        if(userId !=null){
            spec = spec.and(TimePointsSpecifications.executorIdEquals(userId));
        }
        if(taskId !=null){
            spec = spec.and(TimePointsSpecifications.taskIdEquals(taskId));
        }
        if(from != null) {
            spec = spec.and(TimePointsSpecifications.timeGreaterOrEqualsThan(from));

        }
        if(to != null) {
            spec = spec.and(TimePointsSpecifications.timeLessOrEqualsThan(to));

        }

        return timePointRepository.findAll(spec, PageRequest.of(page - 1, 100));

    }
}
