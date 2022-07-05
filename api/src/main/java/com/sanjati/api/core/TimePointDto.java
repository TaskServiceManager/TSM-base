package com.sanjati.api.core;

public class TimePointDto {

    private Long id;

    private Long processId;

    private Long executorId;

    private Boolean isAtWork;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }

    public Boolean getAtWork() {
        return isAtWork;
    }

    public void setAtWork(Boolean atWork) {
        isAtWork = atWork;
    }
}
