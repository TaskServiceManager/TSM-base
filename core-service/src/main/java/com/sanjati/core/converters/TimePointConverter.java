package com.sanjati.core.converters;

import com.sanjati.api.core.TimePointDtoRs;
import com.sanjati.core.entities.TimePoint;
import org.springframework.stereotype.Component;

@Component
public class TimePointConverter {

    public TimePointDtoRs entityToDto(TimePoint entity){
        TimePointDtoRs dto = new TimePointDtoRs();
        dto.setId(entity.getId());
        dto.setTaskId(entity.getTask().getId());
        dto.setExecutorId(entity.getExecutorId());
        dto.setStatus(entity.getStatus().toString());
        return dto;
    }
}
