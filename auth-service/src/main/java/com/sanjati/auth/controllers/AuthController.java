package com.sanjati.auth.controllers;


import com.sanjati.api.auth.UserDtoRs;

import com.sanjati.api.auth.UserLightDto;
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

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final String PATH_AUTH = "/auth";
    private final String PATH_DATA = "/data";
    private final String PATH_USER = "/user";

    private final UserConverter userConverter;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Operation(
            summary = "Авторизация",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping(PATH_AUTH)
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
    @GetMapping(PATH_DATA)
    public UserDtoRs getFullUserDataById(@RequestParam Long userId){
        User user = userService.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        return userConverter.modelToDto(user);
    }

    @GetMapping(PATH_USER)
    public UserLightDto getUserLightByUserId(@RequestParam Long userId){
        User user = userService.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        return userConverter.modelToLightDto(user);
    }

}
