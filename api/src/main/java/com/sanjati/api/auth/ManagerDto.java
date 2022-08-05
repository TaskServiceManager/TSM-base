package com.sanjati.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;



public class ManagerDto {

    @Schema(description = "ID менеджера", example = "3")
    private Long id;

    @Schema(description = "Имя менеджера", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия менеджера", example = "Иванов")
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ManagerDto(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ManagerDto() {
    }
}
