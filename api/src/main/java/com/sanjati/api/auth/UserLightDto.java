package com.sanjati.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Короткая информация о пользователе")
public class UserLightDto {
    @Schema(description = "ID пользователя", example = "3")
    private Long id;
    @Schema(description = "Имя пользователя", example = "Дмитрий")
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Дмитриев")
    private String lastName;
    @Schema(description = "Отчество пользователя", example = "Дмитриевич")
    private String middleName;
    @Schema(description = "E-mail пользователя", example = "ddd@mail.ru")
    private String email;

    public UserLightDto() {
    }

    public UserLightDto(Long id, String firstName, String lastName, String middleName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
    }

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
