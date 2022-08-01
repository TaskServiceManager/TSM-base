package com.sanjati.core.services;

import com.sanjati.api.core.WorkDayDtoRq;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.converters.WorkDayConverter;
import com.sanjati.core.entities.WorkDay;
import com.sanjati.core.exceptions.WorkDayException;
import com.sanjati.core.repositories.WorkDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final WorkDayConverter workDayConverter;

    public void createWorkDay(Long executorId, WorkDayDtoRq workDay) {
        WorkDay lastWorkDay = workDayRepository.findLastWorkDayByExecutorId(executorId).orElse(null);
        //если текущее время меньше, чем время окончания последнего рабочего дня
        if ((lastWorkDay != null && LocalDateTime.now().compareTo(lastWorkDay.getEnd()) <= 0))
            throw new WorkDayException("Нельзя начать новый рабочий день пока не закончился старый.");

        if (LocalDateTime.now().compareTo(workDay.getEnd()) >= 0)
            throw new WorkDayException("Время окончания рабочего дня не может быть меньше текущего времени.");

        WorkDay newWorkDay = workDayConverter.dtoToEntity(workDay);
        newWorkDay.setExecutorId(executorId);
        workDayRepository.save(newWorkDay);
    }

    public void changeEndWorkDay(Long executorId, LocalDateTime newEndWorkDay) {
        WorkDay lastWorkDay = workDayRepository.findLastWorkDayByExecutorId(executorId).
                orElseThrow(()-> new ResourceNotFoundException("У вас пока нет ни одного рабочего дня. Начните свой первый рабочий день!"));
        if (LocalDateTime.now().compareTo(lastWorkDay.getEnd()) >= 0)
            throw new WorkDayException("Нельзя изменить уже законченный день. Начните новый.");

        if (lastWorkDay.getStart().compareTo(newEndWorkDay) >= 0)
            throw new WorkDayException("Новое время не может быть меньше или равно времени начала рабочего дня.");
        lastWorkDay.setEnd(newEndWorkDay);
        workDayRepository.save(lastWorkDay);
    }
}
