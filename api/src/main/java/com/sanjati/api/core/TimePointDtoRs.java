package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

public class TimePointDtoRs {
    @Schema(description = "ID временной отметки", example = "3")
    private Long id;
    @Schema(description = "ID задачи", example = "3")
    private Long taskId;
    @Schema(description = "ID исполнителя выполняющего задачу", example = "3")
    private Long executorId;
    @Schema(description = "статус", example = "IN_PROCESS")
    private String status;

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

    public TimePointDtoRs(Long id, Long taskId, Long executorId, String status) {
        this.id = id;
        this.taskId = taskId;
        this.executorId = executorId;
        this.status = status;
    }

    public TimePointDtoRs() {
    }
}
