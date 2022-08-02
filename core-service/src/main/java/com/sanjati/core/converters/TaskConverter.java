package com.sanjati.core.converters;

import com.sanjati.api.core.TaskDto;
import com.sanjati.api.auth.UserLightDto;

import com.sanjati.core.entities.Task;
import com.sanjati.core.integrations.AuthServiceIntegration;
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

    private final AuthServiceIntegration authServiceIntegration;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String formatDate(LocalDateTime dateTime) {
        return dateTime!=null ? dateTime.format(formatter) : null;
    }

    public TaskDto entityToDto(Task entity) {

        List<UserLightDto> executorsList = new ArrayList<>();
        if(!isNull(entity.getExecutors())) {
            entity.getExecutors().forEach(ex -> {
                UserLightDto userLightDto = authServiceIntegration.getUserLightById(ex);
                if(userLightDto!=null) {
                    executorsList.add(userLightDto);
                }
            });
        }

        String formattedCreatedAt = formatDate(entity.getCreatedAt());
        String formattedCompletedAt = formatDate(entity.getCompletedAt());
        String formattedUpdatedAt = formatDate(entity.getUpdatedAt());

        return TaskDto.builder()
                .id(entity.getId())
                .status(entity.getStatus().getRus())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .owner(entity.getOwnerId()!=null ? authServiceIntegration.getUserLightById(entity.getOwnerId()) : null)
                .executors(executorsList)
                .chief(entity.getChiefId() !=null ? authServiceIntegration.getUserLightById(entity.getChiefId()) : null)
                .createdAt(formattedCreatedAt)
                .completedAt(formattedCompletedAt)
                .updatedAt(formattedUpdatedAt)
                .build();
    }
}
