package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Schema(description = "Тело запроса для создания комментария")
public class CommentRq {
    @NotBlank(message = "Необходимо указать id завски")
    @Schema(description = "ID заявки", example = "3",required = true)
    private Long taskId;
    @NotBlank(message = "Необходимо что-то написать")
    @Schema(description = "Комментарий сотрудника", example = "Съел всю морковку, работать дальше невозможно",required = true)
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
