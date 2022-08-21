package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Schema(description = "Тело запроса для создания комментария")
public class CommentRq {
    @Positive(message = "Необходимо указать id заявки")
    @NotNull(message = "Необходимо указать id заявки")
    @Schema(description = "ID заявки", example = "3",required = true)
    private Long taskId;
    @NotBlank(message = "Необходимо что-то написать")
    @NotNull(message = "Необходимо что-то написать")
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
