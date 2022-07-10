package com.sanjati.core.converters;

import com.sanjati.api.core.TaskDto;
import com.sanjati.core.entities.Executor;
import com.sanjati.core.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class TaskConverter {


    private final CommentConverter commitsConverter;

    public TaskDto entityToDto(Task entity) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<String> executorsList = new ArrayList<>();
        if(!isNull(entity.getExecutors())) {
            executorsList = entity.getExecutors().stream().map(Executor::getName).collect(Collectors.toList());
        }

        return TaskDto.builder()
                .id(entity.getId())
                .status(entity.getStatus().getRus())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .ownerId(entity.getOwnerId())
                .ownerName(entity.getOwnerName())
                .executors(executorsList)
                .createdAt(entity.getCreatedAt()!=null ? entity.getCreatedAt().format(formatter) : null)
                .completedAt(entity.getCompletedAt()!=null ? entity.getCompletedAt().format(formatter) : null)
                .build();
    }
}
