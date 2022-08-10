package com.sanjati.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос токена")
public class JwtRequest {

    @Schema(description = "Имя пользователя")
    private String username;

    @Schema(description = "Пароль пользователя")
    private String password;
}
