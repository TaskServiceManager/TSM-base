package com.sanjati.api.core;

public class CommitDto {

    private Long id;

    private Long orderId;

    private String executorCommit;

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

    public String getExecutorCommit() {
        return executorCommit;
    }

    public void setExecutorCommit(String executorCommit) {
        this.executorCommit = executorCommit;
    }
}
