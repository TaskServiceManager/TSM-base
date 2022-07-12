package com.sanjati.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

public class ExecutorDto {
    @Schema(description = "ID исполнителя", example = "3")
    private Long id;
    @Schema(description = "Краткое имя исполнителя", example = "Иванов И.И.")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExecutorDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ExecutorDto() {
    }
}
