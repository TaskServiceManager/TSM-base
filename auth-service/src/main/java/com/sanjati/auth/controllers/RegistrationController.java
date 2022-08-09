package com.sanjati.auth.controllers;


import com.sanjati.api.auth.NewUserDtoRq;
import com.sanjati.auth.services.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @Operation(
            summary = "Регистрация",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    public void registration(@Parameter(description = "Тело запроса запроса на регистрацию", required = true)@RequestBody NewUserDtoRq newUserDto) {
        registrationService.createNewUser(newUserDto);
    }
}
