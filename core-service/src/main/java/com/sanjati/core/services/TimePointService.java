package com.sanjati.core.services;


import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.exceptions.MandatoryCheckException;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.TimePoint;
import com.sanjati.core.enums.TaskStatus;
import com.sanjati.core.enums.TimePointStatus;
import com.sanjati.core.repositories.TimePointRepository;
import com.sanjati.core.repositories.specifications.TimePointsSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class TimePointService {
    private final TimePointRepository timePointRepository;
    private final TaskService taskService;
    private final AuthService authService;

    public TimePointService(TimePointRepository timePointRepository, @Lazy TaskService taskService,
                            AuthService authService) {
        this.timePointRepository = timePointRepository;
        this.taskService = taskService;
        this.authService = authService;
    }

    @Transactional
    public void changeStatusOrCreateTimePoint(Long taskId, Long userId, Long timePointId) {
        UserLightDto userLightDto    = authService.getUserLightById(userId);

        if (timePointId != null) {
            TimePoint tp = timePointRepository.findByIdAndStatus(timePointId,TimePointStatus.IN_PROCESS).orElseThrow(()->new ResourceNotFoundException("Отметка не существует ID : " + timePointId));

            tp.setStatus(TimePointStatus.FINISHED);
            tp.setFinishedAt(LocalDateTime.now());
            return;

        }

        if(timePointRepository.existsByExecutorIdAndStatus(userId,TimePointStatus.IN_PROCESS)) {
            throw new MandatoryCheckException("Нельзя открыть новую отметку, пока есть незавершённые");
        }

        if (LocalTime.now().isBefore(userLightDto.getStartWorkTime())
                ||
                LocalTime.now().isAfter(userLightDto.getEndWorkTime())) {
            throw new MandatoryCheckException("Ваше рабочее время указано в профиле. Вы не можете выполнять заявку в нерабочее время");
        }

        if(TaskStatus.ASSIGNED==taskService.getStatusByTaskId(taskId)){
            taskService.changeStatus(taskId, TaskStatus.ACCEPTED);
        }
        TimePoint tp = new TimePoint();
        tp.setStatus(TimePointStatus.IN_PROCESS);
        tp.setExecutorId(userId);
        tp.setTaskId(taskId);
        tp.setFinishedAt(correctFinishedAtForTimePoint(userLightDto.getEndWorkTime()));
        timePointRepository.save(tp);

    }

    @Transactional
    public void closeTimePointByTaskAndExecutorId(Long taskId, Long executorId) {
        TimePoint timePoint = timePointRepository.findByTaskIdAndExecutorIdAndStatus(taskId, executorId, TimePointStatus.IN_PROCESS).orElseThrow(()-> new MandatoryCheckException("открытых отметок не найдено"));

        // убрал if  так как мы и так вынули тайм поинт по статусу
        timePoint.setStatus(TimePointStatus.FINISHED);
        timePoint.setFinishedAt(LocalDateTime.now());
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

        return timePointRepository.findAll(spec, PageRequest.of(page - 1, 100, Sort.by(Sort.Direction.DESC, "startedAt")));
    }

    public TimePoint getCurrentTimePointByUserId(Long userId) {
        return timePointRepository.findFirstByExecutorIdAndStatus(userId,TimePointStatus.IN_PROCESS).stream().findFirst().orElse(null);
    }

    public boolean checkTimePointsStatusByTaskId(Long taskId) {
        return timePointRepository.existsByTaskIdAndStatus(taskId,TimePointStatus.IN_PROCESS);
    }

    @Scheduled(fixedRateString = "${interval.closingTimePoints}")
    @Transactional
    public void autoClosingTimePoints() {
        timePointRepository.updateAllStatusByFinishedAt(TimePointStatus.FINISHED);
    }

    /*
    Время окончания работы у сотрудника может быть в 2022-08-02T00:34, а автоматическое закрытие происходить
    в 2022-08-01T23:45. В этом случае переменная время закрытия таймпоинта получится 2022-08-01T00:34 - это не корректно.
    Поэтому необходимо увеличить это значение на 1 день.
     */
    private LocalDateTime correctFinishedAtForTimePoint(LocalTime endWorkTime){
        LocalDateTime finishedAtTP = LocalDateTime.of(LocalDate.now(), endWorkTime);
        if (LocalDateTime.now().isAfter(finishedAtTP)){
            finishedAtTP = finishedAtTP.plusDays(1);
        }
        return finishedAtTP;
    }
}
