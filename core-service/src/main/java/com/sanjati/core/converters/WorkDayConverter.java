package com.sanjati.core.converters;

import com.sanjati.api.core.WorkDayDtoRq;
import com.sanjati.core.entities.WorkDay;
import org.springframework.stereotype.Component;

@Component
public class WorkDayConverter {

    public WorkDay dtoToEntity(WorkDayDtoRq dto){
        return new WorkDay(dto.getEnd());
    }
}
