package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Тело запроса для назначения исполнителей по заявке")
public class AssignDtoRq {
    @NotNull(message = "Необходимо указать Список ID исполнителей")
    @NotEmpty(message = "Необходимо указать Список ID исполнителей")
    @Schema(description = "Список ID исполнителей", required = true)
    private List<Long> executorIds;
    @NotBlank(message = "Необходимо указать Список ID исполнителей")
    @NotNull(message = "Необходимо указать отвественного")
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
