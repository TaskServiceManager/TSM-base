package com.sanjati.api.core;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;


@Schema(description = "Тело запроса с параметрами поиска для получения задач")
public class SearchParamsTaskDtoRq {

    @Schema(description = "ID автора заявки", example = "3")
    private Long ownerId;
    @Positive(message = "номер строго больше нуля")
    @NotBlank(message = "Необходимо указать номер страницы")
    @Schema(description = "Номер страницы", example = "1", required = true)
    private Integer page;

    @Schema(description = "Граница по времени ОТ", example = "2022-08-01T14:15")
    private LocalDateTime from;

    @Schema(description = "Граница по времени ДО", example = "2022-08-01T20:22")
    private LocalDateTime to;

    @Schema(description = "Статус заявок", example = "ACCEPTED")
    private String status;

    @Schema(description = "ID исполнителя", example = "5")
    private Long executorId;

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public void setExecutorId(Long executorId) {
        this.executorId = executorId;
    }
}
