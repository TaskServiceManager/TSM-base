package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;


@Schema(description = "Тело запроса для создания задачи")

public class TaskDtoRq {
    @NotBlank(message = "Необходимо указать заголовок")
    @Schema(description = "Заголовок", required = true)
    private String title;
    @NotBlank(message = "Необходимо описание")
    @Schema(description = "Описание задачи", required = true)
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskDtoRq(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public TaskDtoRq() {
    }
}
