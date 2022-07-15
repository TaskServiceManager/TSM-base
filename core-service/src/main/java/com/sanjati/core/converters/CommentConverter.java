package com.sanjati.core.converters;

import com.sanjati.api.core.CommentDtoRs;
import com.sanjati.core.entities.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public CommentDtoRs entityToDto(Comment entity){
        CommentDtoRs dto = new CommentDtoRs();
        dto.setId(entity.getId());
        dto.setTaskId(entity.getTaskId());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
