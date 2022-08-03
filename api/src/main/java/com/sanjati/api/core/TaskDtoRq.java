package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

public class TaskDtoRq {
    @Schema(description = "Имя задачи")
    private String title;

    @Schema(description = "Описание задачи")
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
