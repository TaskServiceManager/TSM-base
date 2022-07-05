package com.sanjati.core.converters;

import com.sanjati.api.core.ProcessDto;
import com.sanjati.core.entities.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProcessConverter {

    private final TimePointConverter timePointConverter;

    public ProcessDto entityToDto(Process entity){
        ProcessDto dto = new ProcessDto();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrder().getId());
        dto.setActive(entity.getIsActive());
        dto.setOnConfirm(entity.getOnConfirm());
        dto.setExecutorId(entity.getExecutorId());
        dto.setTimePoints(entity.getTimePoints().stream().map(timePointConverter::entityToDto).collect(Collectors.toList()));
        dto.setExecutorShortName(entity.getExecutorShortName());
        dto.setExecutorLongName(entity.getExecutorLongName());
        dto.setTask(entity.getTask());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
