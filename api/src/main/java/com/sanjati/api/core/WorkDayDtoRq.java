package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Тело запроса для создания нового рабочего дня")
public class WorkDayDtoRq {

    @Schema(description = "Конец рабочего дня.", example = "2022-07-31T21:49")
    private LocalDateTime end;

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

}
