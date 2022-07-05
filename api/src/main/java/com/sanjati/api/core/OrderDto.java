package com.sanjati.api.core;




import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Основная модель задачи")
public class OrderDto {

    @Schema(description = "ID задачи", example = "3")
    private Long id;

    @Schema(description = "Показывает активна ли заявка", example = "true")
    private Boolean isActive;

    @Schema(description = "Процессы выполнения заявки")
    private List<ProcessDto> processes;

    @Schema(description = "Комментарии сотрудников по выполнению")
    private List<CommitDto> commits;

    @Schema(description = "Название заявки", example = "Сделать командный проект")
    private String title;

    @Schema(description = "Описание заявки", example = "Необходимо то-то и то-то...")
    private String description;

    @Schema(description = "Никнейм создателя заявки", example = "pepe777")
    private String userNick;

    @Schema(description = "ID создателя заявки", example = "2")
    private Long userId;

    @Schema(description = "Краткое имя создателя заявки", example = "Иванов И.И.")
    private String userShortName;

    @Schema(description = "Полное имя создателя заявки", example = "Иванов Иван Иванович")
    private String userLongName;

    @Schema(description = "Статус заявки", example = "accepted")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<ProcessDto> getProcesses() {
        return processes;
    }

    public void setProcesses(List<ProcessDto> processes) {
        this.processes = processes;
    }

    public List<CommitDto> getCommits() {
        return commits;
    }

    public void setCommits(List<CommitDto> commits) {
        this.commits = commits;
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

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserShortName() {
        return userShortName;
    }

    public void setUserShortName(String userShortName) {
        this.userShortName = userShortName;
    }

    public String getUserLongName() {
        return userLongName;
    }

    public void setUserLongName(String userLongName) {
        this.userLongName = userLongName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
