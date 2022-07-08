package com.sanjati.core.converters;

import com.sanjati.api.core.TaskDto;
import com.sanjati.core.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskConverter {


    private final CommentConverter commitsConverter;

    public TaskDto entityToDto(Task entity) {
       TaskDto dto = new TaskDto();
       dto.setId(entity.getId());
       dto.setStatus(entity.getStatus().name());
       dto.setTitle(entity.getTitle());
       dto.setDescription(entity.getDescription());
       dto.setOwnerId(entity.getOwnerId());
       dto.setOwnerName(entity.getOwnerName());
       return dto;

    }
}
