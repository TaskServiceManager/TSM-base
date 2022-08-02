package com.sanjati.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDto {

    @Schema(description = "ID менеджера", example = "3")
    private Long id;

    @Schema(description = "Имя менеджера", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия менеджера", example = "Иванов")
    private String lastName;

}
