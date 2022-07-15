package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель комментария")
public class CommentDtoRs {
    @Schema(description = "ID комментария", example = "3")
    private Long id;
    @Schema(description = "ID задачи", example = "3")
    private Long taskId;
    @Schema(description = "комментарий сотрудника или системы", example = "съел всю морковку, работать дальше невозможно")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommentDtoRs(Long id, Long taskId, String description) {
        this.id = id;
        this.taskId = taskId;
        this.description = description;
    }

    public CommentDtoRs() {
    }
}
