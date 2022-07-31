package com.sanjati.core.converters;

import com.sanjati.api.core.TimePointDto;
import com.sanjati.core.entities.TimePoint;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimePointConverter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TimePointDto entityToDto(TimePoint entity){
        if(entity==null) {
            return null;
        }
        String formattedStartedAt = formatDate(entity.getStartedAt());
        String formattedFinishedAt = formatDate(entity.getFinishedAt());

        TimePointDto dto = new TimePointDto();
        dto.setId(entity.getId());
        dto.setTaskId(entity.getId());
        dto.setExecutorId(entity.getExecutorId());
        dto.setStatus(entity.getStatus().toString());
        dto.setStartedAt(formattedStartedAt);
        dto.setFinishedAt(formattedFinishedAt);
        return dto;
    }

    private String formatDate(LocalDateTime dateTime) {
        return dateTime!=null ? dateTime.format(formatter) : null;
    }
}
