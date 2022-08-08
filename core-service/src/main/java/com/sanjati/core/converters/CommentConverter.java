package com.sanjati.core.converters;

import com.sanjati.api.core.CommentDto;
import com.sanjati.core.entities.Comment;
import com.sanjati.core.integrations.AuthServiceIntegration;
import com.sanjati.core.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;


@Component
@RequiredArgsConstructor
public class CommentConverter {

    private final AuthService authService;

    public CommentDto entityToDto(Comment entity){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setTaskId(entity.getTaskId());
        dto.setAuthor(entity.getAuthorId()!=null ? authService.getUserLightById(entity.getAuthorId()) : null);
        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt()!=null ? entity.getCreatedAt().format(formatter) : null);
        return dto;
    }
}
