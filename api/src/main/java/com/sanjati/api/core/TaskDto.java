package com.sanjati.api.core;
import com.sanjati.api.auth.UserLightDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "Основная модель задачи")
public class TaskDto {

    @Schema(description = "ID задачи", example = "3")
    private Long id;

    @Schema(description = "Показывает в каком состоянии находится заявка", example = "CANCELLED")
    private String status;

    @Schema(description = "Название заявки", example = "Сделать командный проект")
    private String title;

    @Schema(description = "Описание заявки", example = "Необходимо то-то и то-то...")
    private String description;

    @Schema(description = "Создатель заявки")
    private UserLightDto owner;

    @Schema(description = "Список назначенных исполнителей", example = "2022-07-10 16:30:19")
    private List<UserLightDto> executors;

    @Schema(description = "Назначенный ответственным, исполнитель по заявке")
    private UserLightDto chief;

    @Schema(description = "Дата создания заявки", example = "2022-07-10 16:30:19")
    private String createdAt;

    @Schema(description = "Дата выполнения заявки", example = "2022-07-10 16:30:19")
    private String completedAt;

    @Schema(description = "Дата последнего обновления", example = "2022-07-10 16:30:19")
    private String updatedAt;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UserLightDto> getExecutors() {
        return executors;
    }

    public void setExecutors(List<UserLightDto> executors) {
        this.executors = executors;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public UserLightDto getOwner() {
        return owner;
    }

    public void setOwner(UserLightDto owner) {
        this.owner = owner;
    }

    public UserLightDto getChief() {
        return chief;
    }

    public void setChief(UserLightDto chief) {
        this.chief = chief;
    }

    public TaskDto(Long id, String status, String title, String description, UserLightDto owner, List<UserLightDto> executors, UserLightDto chief, String createdAt, String completedAt, String updatedAt) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.executors = executors;
        this.chief = chief;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.updatedAt = updatedAt;
    }

    public TaskDto() {
    }
}
