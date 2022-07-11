package com.sanjati.api.core;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "Основная модель задачи")
public class TaskDto {

    @Schema(description = "ID задачи", example = "3")
    private Long id;

    @Schema(description = "Показывает в каком состоянии находится заявка", example = "CANCELLED")
    private String status;

   // удалил процессы выполнения

//    @Schema(description = "Комментарии сотрудников по выполнению")
//    private List<CommentDto> comments;// тут насколько я знаю если поместить в лист что-то кроме стринга могут быть проблемы с мапингом объекта
//    @Schema(description = "Временные отметки сотрудников")
//    private List<TimePointDto> timePoints;// тут насколько я знаю если поместить в лист что-то кроме стринга могут быть проблемы с мапингом объекта

    @Schema(description = "Название заявки", example = "Сделать командный проект")
    private String title;

    @Schema(description = "Описание заявки", example = "Необходимо то-то и то-то...")
    private String description;

    @Schema(description = "ID создателя заявки", example = "2")
    private Long ownerId;

    @Schema(description = "Краткое имя создателя заявки", example = "Иванов И.И.")
    private String ownerName;

    @Schema(description = "Список назначенных исполнителей", example = "2022-07-10 16:30:19")
    private List<String> executors;

    @Schema(description = "Дата создания заявки", example = "2022-07-10 16:30:19")
    private String createdAt;

    @Schema(description = "Дата выполнения заявки", example = "2022-07-10 16:30:19")
    private String completedAt;

    @Schema(description = "Дата последнего обновления", example = "2022-07-10 16:30:19")
    private String updatedAt;

//    public List<CommentDto> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<CommentDto> comments) {
//        this.comments = comments;
//    }
//
//    public List<TimePointDto> getTimePoints() {
//        return timePoints;
//    }
//
//    public void setTimePoints(List<TimePointDto> timePoints) {
//        this.timePoints = timePoints;
//    }
//


    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getExecutors() {
        return executors;
    }

    public void setExecutors(List<String> executors) {
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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public TaskDto() {
    }

    public TaskDto(Long id, String status, String title, String description, Long ownerId, String ownerName, List<String> executors, String createdAt, String completedAt, String updatedAt) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.executors = executors;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        this.updatedAt = updatedAt;
    }
}
