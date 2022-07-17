package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Тело запроса для создания комментария")
public class CommentRq {
    @Schema(description = "ID заявки", example = "3")
    private Long taskId;
    @Schema(description = "Комментарий сотрудника", example = "Съел всю морковку, работать дальше невозможно")
    private String description;

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

}
