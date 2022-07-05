package com.sanjati.api.core;

import java.util.List;

public class ProcessDto {

    private Long id;

    private Long orderId;

    private Boolean isActive;

    private Boolean onConfirm;

    private Long executorId;

    private List<TimePointDto> timePoints;

    private String executorShortName;

    private String executorLongName;

    private String task;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getOnConfirm() {
        return onConfirm;
    }

    public void setOnConfirm(Boolean onConfirm) {
        this.onConfirm = onConfirm;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    public List<TimePointDto> getTimePoints() {
        return timePoints;
    }

    public void setTimePoints(List<TimePointDto> timePoints) {
        this.timePoints = timePoints;
    }

    public String getExecutorShortName() {
        return executorShortName;
    }

    public void setExecutorShortName(String executorShortName) {
        this.executorShortName = executorShortName;
    }

    public String getExecutorLongName() {
        return executorLongName;
    }

    public void setExecutorLongName(String executorLongName) {
        this.executorLongName = executorLongName;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
