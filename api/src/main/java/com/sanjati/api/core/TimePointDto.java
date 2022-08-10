package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Основная модель временной отметки")
public class TimePointDto {
    @Schema(description = "ID временной отметки", example = "3")
    private Long id;
    @Schema(description = "ID задачи", example = "3")
    private Long taskId;
    @Schema(description = "ID исполнителя выполняющего задачу", example = "3")
    private Long executorId;
    @Schema(description = "Статус", example = "В процессе")
    private String status;
    @Schema(description = "Дата начала работы", example = "2022-07-10 16:30:19")
    private String startedAt;
    @Schema(description = "Дата окончания работы", example = "2022-07-10 16:30:19")
    private String finishedAt;
    @Schema(description = "Затраченное время", example = "44:50:00")
    private String duration;

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

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public TimePointDto(Long id, Long taskId, Long executorId, String status, String startedAt, String finishedAt, String duration) {
        this.id = id;
        this.taskId = taskId;
        this.executorId = executorId;
        this.status = status;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.duration = duration;
    }

    public TimePointDto() {
    }
}
