package com.sanjati.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

@Schema(description = "Тело запроса для изменения рабочего времени исполнителя")
public class WorkTimeDtoRq {


    @Schema(description = "Начало рабочего дня.", example = "09:21:23")
    private LocalTime startWorkTime;
    @Schema(description = "Конец рабочего дня.", example = "21:49:56")
    private LocalTime endWorkTime;

    public LocalTime getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(LocalTime startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public LocalTime getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(LocalTime endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

}
