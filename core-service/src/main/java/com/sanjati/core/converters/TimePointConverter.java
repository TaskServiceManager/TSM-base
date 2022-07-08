package com.sanjati.core.converters;

import com.sanjati.api.core.TimePointDto;
import com.sanjati.core.entities.TimePoint;
import org.springframework.stereotype.Component;

@Component
public class TimePointConverter {

    public TimePointDto entityToDto(TimePoint entity){
        TimePointDto dto = new TimePointDto();
        dto.setId(entity.getId());
        dto.setProcessId(entity.getProcess().getId());
        dto.setExecutorId(entity.getExecutorId());
        dto.setAtWork(entity.getIsAtWork());
        return dto;
    }
}
