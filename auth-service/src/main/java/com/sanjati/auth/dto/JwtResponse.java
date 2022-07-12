package com.sanjati.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Ответ токена")
public class JwtResponse {

    @Schema(description = "Токен")
    private String token;
}
