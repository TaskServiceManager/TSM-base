package com.sanjati.api.core;

import com.sanjati.api.auth.UserLightDto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель комментария")
public class CommentDto {
    @Schema(description = "ID комментария", example = "3")
    private Long id;
    @Schema(description = "ID задачи", example = "3")
    private Long taskId;
    @Schema(description = "Автор")
    private UserLightDto author;
    @Schema(description = "комментарий сотрудника или системы", example = "съел всю морковку, работать дальше невозможно")
    private String description;
    @Schema(description = "Дата создания комментария", example = "2022-07-10 16:30:19")
    private String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

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

    public UserLightDto getAuthor() {
        return author;
    }

    public void setAuthor(UserLightDto author) {
        this.author = author;
    }

    public CommentDto(Long id, Long taskId, UserLightDto author, String description, String createdAt) {
        this.id = id;
        this.taskId = taskId;
        this.author = author;
        this.description = description;
        this.createdAt = createdAt;
    }

    public CommentDto() {
    }
}
