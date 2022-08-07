package com.sanjati.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;


@Schema(description = "Очень короткая информация о пользователе")
public class UserTinyDto {
    @Schema(description = "ID пользователя", example = "3")
    private Long id;
    @Schema(description = "Короткое имя пользователя", example = "userHi")
    private String username;
    @Schema(description = "Фамилия и инициалы", example = "Титов П.У.")
    private String shortName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public UserTinyDto(Long id, String username, String shortName) {
        this.id = id;
        this.username = username;
        this.shortName = shortName;
    }

    public UserTinyDto() {
    }
}
