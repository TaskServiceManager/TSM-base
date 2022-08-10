package com.sanjati.core.converters;

import com.sanjati.api.core.TaskDto;
import com.sanjati.api.auth.UserLightDto;

import com.sanjati.core.entities.Task;
import com.sanjati.core.integrations.AuthServiceIntegration;
import com.sanjati.core.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class TaskConverter {

    private final AuthService authService;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String formatDate(LocalDateTime dateTime) {
        return dateTime!=null ? dateTime.format(formatter) : null;
    }

    public TaskDto entityToDto(Task entity) {

        List<UserLightDto> executorsList = new ArrayList<>();
        if(!isNull(entity.getExecutors())) {
            List<UserLightDto> userLightDtos = authService.getUserLightListByIds(entity.getExecutors());
            if(userLightDtos!=null) {
                executorsList.addAll(userLightDtos);
            }
        }

        String formattedCreatedAt = formatDate(entity.getCreatedAt());
        String formattedCompletedAt = formatDate(entity.getCompletedAt());
        String formattedUpdatedAt = formatDate(entity.getUpdatedAt());
        TaskDto taskDto = new TaskDto();
        taskDto.setId(entity.getId());
        taskDto.setStatus(entity.getStatus().getRus());
        taskDto.setTitle(entity.getTitle());
        taskDto.setDescription(entity.getDescription());
        taskDto.setOwner(entity.getOwnerId()!=null ? authService.getUserLightById(entity.getOwnerId()) : null);
        taskDto.setExecutors(executorsList);
        taskDto.setChief(entity.getChiefId() !=null ? authService.getUserLightById(entity.getChiefId()) : null);
        taskDto.setCreatedAt(formattedCreatedAt);
        taskDto.setCompletedAt(formattedCompletedAt);
        taskDto.setUpdatedAt(formattedUpdatedAt);
        return taskDto;
    }
}
