package com.sanjati.auth.controllers;


import com.sanjati.api.auth.NewUserDtoRq;
import com.sanjati.api.auth.UserTinyDto;
import com.sanjati.auth.services.AdministrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdministrationController {
    private final AdministrationService administrationService;

    @Operation(
            summary = "Запрос на изменение данных пользователя в том числе и пароля",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404"
                    )
            }
    )
    @PutMapping("/users/{id}")
    public void updateUser(@Parameter(description = "ID пользователя")
                               @PathVariable(name = "id") Long userId,
                               @Parameter(description = "Тело запроса с изменениями в профиль пользователя")
                               @RequestBody NewUserDtoRq update){
        administrationService.changeUserData(update,userId);
    }

    @Operation(
            summary = "Запрос на изменение роли пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404"
                    )
            }
    )
    @PatchMapping("/users/{id}/roles")
    public void updateRole(@Parameter(description = "ID пользователя")
                               @PathVariable(name = "id") Long userId,
                               @Parameter(description = "Список ролей")
                               @RequestBody List<String> roles){
        administrationService.changeRoles(roles, userId);
    }

    @Operation(
            summary = "Запрос на получение страницы со всеми пользователями удовлетворяющих условиям",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )

    @GetMapping("/users")
    public Page<UserTinyDto> getUsersBySpec(@Parameter(description = "номер страницы", required = true)
                                           @RequestParam Integer page,
                                            @Parameter(description = "ID пользователя", required = false)
                                           @RequestParam(required = false) Long id,
                                            @Parameter(description = "username пользователя или его часть", required = false)
                                           @RequestParam(required = false) String usernamePart,
                                            @Parameter(description = "роль пользователя", required = false)
                                           @RequestParam(required = false) String roleName){
        return administrationService.findUsersBySpec(id, usernamePart, roleName, page);
    }
}
