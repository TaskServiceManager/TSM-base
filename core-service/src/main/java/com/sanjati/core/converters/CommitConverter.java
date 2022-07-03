package com.sanjati.core.converters;

import com.sanjati.api.core.CommitDto;
import com.sanjati.core.entities.Commit;
import org.springframework.stereotype.Component;

@Component
public class CommitConverter {

    public CommitDto entityToDto(Commit entity){
        CommitDto dto = new CommitDto();
        dto.setId(entity.getId());
        dto.setOrderId(entity.getOrder().getId());
        dto.setExecutorCommit(entity.getExecutorCommit());
        return dto;
    }
}
