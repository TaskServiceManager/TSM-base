package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Тело запроса для назначения исполнителей по заявке")
public class AssignDtoRq {
    @Schema(description = "Список ID исполнителей", required = true)
    private List<Long> executorIds;
    @Schema(description = "ID ответственного", required = true)
    private Long chiefId;

    public AssignDtoRq(List<Long> executorIds, Long chiefId) {
        this.executorIds = executorIds;
        this.chiefId = chiefId;
    }

    public List<Long> getExecutorIds() {
        return executorIds;
    }

    public void setExecutorIds(List<Long> executorIds) {
        this.executorIds = executorIds;
    }

    public Long getChiefId() {
        return chiefId;
    }

    public void setChiefId(Long chiefId) {
        this.chiefId = chiefId;
    }
}
