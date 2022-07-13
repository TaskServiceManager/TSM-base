package com.sanjati.core.converters;

import com.sanjati.api.core.TaskDto;

import com.sanjati.core.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class TaskConverter {


    private final CommentConverter commitsConverter;

    public TaskDto entityToDto(Task entity) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Long> executorsList = new ArrayList<>();
        if(!isNull(entity.getExecutors())) {
            executorsList = entity.getExecutors();
        }

        return TaskDto.builder()
                .id(entity.getId())
                .status(entity.getStatus().getRus())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .ownerId(entity.getOwnerId())
                .executors(executorsList)
                .createdAt(entity.getCreatedAt()!=null ? entity.getCreatedAt().format(formatter) : null)
                .completedAt(entity.getCompletedAt()!=null ? entity.getCompletedAt().format(formatter) : null)
                .updatedAt(entity.getUpdatedAt()!=null ? entity.getUpdatedAt().format(formatter) : null)
                .build();
    }
}
