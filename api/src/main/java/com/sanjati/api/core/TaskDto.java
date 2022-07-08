package com.sanjati.api.core;




import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

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
//    @Schema(description = "Список занятых сотрудников")
//    private List<ExecutorDto> executors;// тут насколько я знаю если поместить в лист что-то кроме стринга могут быть проблемы с мапингом объекта


    @Schema(description = "Название заявки", example = "Сделать командный проект")
    private String title;

    @Schema(description = "Описание заявки", example = "Необходимо то-то и то-то...")
    private String description;



    @Schema(description = "ID создателя заявки", example = "2")
    private Long ownerId;

    @Schema(description = "Краткое имя создателя заявки", example = "Иванов И.И.")
    private String ownerName;





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
//    public List<ExecutorDto> getExecutors() {
//        return executors;
//    }
//
//    public void setExecutors(List<ExecutorDto> executors) {
//        this.executors = executors;
//    }


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
}
