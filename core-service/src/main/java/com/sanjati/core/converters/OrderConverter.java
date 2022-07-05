package com.sanjati.core.converters;

import com.sanjati.api.core.OrderDto;
import com.sanjati.core.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final ProcessConverter processConverter;
    private final CommitConverter commitsConverter;

    public OrderDto entityToDto(Order entity) {
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setActive(entity.getIsActive());
        dto.setProcesses(entity.getProcesses().stream().map(processConverter::entityToDto).collect(Collectors.toList()));
        dto.setCommits(entity.getCommits().stream().map(commitsConverter::entityToDto).collect(Collectors.toList()));
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setUserNick(entity.getUserNick());
        dto.setUserId(entity.getUserId());
        dto.setUserShortName(entity.getUserShortName());
        dto.setUserLongName(entity.getUserLongName());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
