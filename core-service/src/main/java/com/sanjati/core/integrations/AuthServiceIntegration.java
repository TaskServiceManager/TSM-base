package com.sanjati.core.integrations;


import com.sanjati.api.auth.UserDto;



import com.sanjati.api.auth.UserLightDto;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceIntegration {
    private final WebClient authWebClient;
    private final String DATA_PATH = "api/v1/data";
    private final String USER_PATH = "api/v1/user";
    private final String ALL_USERS_PATH = "api/v1/users";

    public UserDto getUserById(Long userId) {
        return authWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(DATA_PATH)
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .bodyToMono(UserDto.class)
                .doOnError(e -> log.info("Ошибка при получении полной информации о пользователе ", e))
                .block();
    }

    public UserLightDto getUserLightById(Long userId) {
        return authWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(USER_PATH)
                        .queryParam("userId", userId)
                        .build())
                .retrieve()
                .bodyToMono(UserLightDto.class)
                .doOnError(e -> log.info("Ошибка при получении короткой информации о пользователе ", e))
                .block();
    }

    public List<UserLightDto> getAllExecutors(){
        return authWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(ALL_USERS_PATH)
                        .queryParam("role", "ROLE_EXECUTOR")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserLightDto>>() {})
                .doOnError(e -> log.info("Ошибка при получении информации о всех исполнителях"))
                .block();
    }
}
