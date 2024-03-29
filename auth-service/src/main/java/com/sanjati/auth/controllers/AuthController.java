package com.sanjati.auth.controllers;


import com.sanjati.api.auth.UserDto;

import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.auth.WorkTimeDtoRq;

import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.auth.converters.UserConverter;
import com.sanjati.auth.dto.JwtRequest;
import com.sanjati.auth.dto.JwtResponse;
import com.sanjati.auth.entities.User;
import com.sanjati.auth.services.UserService;
import com.sanjati.auth.utils.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final String AUTH_PATH = "/auth";
    private final String DATA_PATH = "/data";
    private final UserConverter userConverter;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Авторизация, назначение токена",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping(AUTH_PATH)
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }


    @Operation(
            summary = "Чтение данных пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )

    @GetMapping("/users/{id}/data")
    public UserDto getFullUserDataById(@Parameter(description = "ID пользователя", required = true)

                                           @PathVariable(name = "id") Long userId){
        User user = userService.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User not found ID : " + userId));
        return userConverter.modelToDto(user);
    }

    @Operation(
            summary = "Запрос на получение короткой информации о пользователе по ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/users/{id}")
    public UserLightDto getUserLightByUserId(@Parameter(description = "ID пользователя", required = true)
                                                 @PathVariable(name = "id") Long userId){
        User user = userService.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User not found ID : " + userId));
        return userConverter.modelToLightDto(user);
    }

    @Operation(
            summary = "Запрос на получение короткой информации обо всех пользователей",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/users")
    public List<UserLightDto> getAllUsers(@Parameter(description = "Роль пользователей")
                                          @RequestParam(required = false) String role) {
        return userService.getAllUsers(role).stream().map(userConverter::modelToLightDto).collect(Collectors.toList());

    }


    @Operation(
            summary = "Запрос на изменение времени работы исполнителя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Исполнитель не найден", responseCode = "404"
                    )
            }
    )
    @PatchMapping("/users/{id}/worktime")
    public void updateWorkTime(@Parameter(description = "ID исполнителя")
                                   @PathVariable(name = "id") Long executorId,
                               @Parameter(description = "Список ролей")
                                   @RequestHeader String role,
                               @Parameter(description = "Тело запроса с новым временем работы")
                                   @RequestBody WorkTimeDtoRq workTimeDtoRq){
        if (!role.contains("ROLE_EXECUTOR")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нет доступа к смене рабочего времени");
        }
        userService.updateWorkTime(executorId, workTimeDtoRq);
    }

    @Operation(
            summary = "Чтение данных пользователей в коротком виде",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping("/users")
    public List<UserLightDto> getLightUserDataById(@Parameter(description = "список ID пользователей", required = true)
                                                   @RequestBody List<Long> usersId) {
        return userService.getLightUserDataById(usersId);

    }

}
