package com.sanjati.core.converters;

import com.sanjati.api.core.CommentDto;
import com.sanjati.core.entities.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public CommentDto entityToDto(Comment entity){
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setTaskId(entity.getTask().getId());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
