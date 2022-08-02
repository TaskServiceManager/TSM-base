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
//    private final String DATA_PATH = "api/v1/data";
//    private final String USER_PATH = "api/v1/users";
//    private final String ALL_USERS_PATH = "api/v1/users"; НЕУДОБНО (и с ошибками)

    public UserDto getUserById(Long userId) {
        return authWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("api/v1/")
                        .path(userId.toString())
                        .path("/data")
                        .build())
                .retrieve()
                .bodyToMono(UserDto.class)
                .doOnError(e -> log.info("Ошибка при получении полной информации о пользователе ", e))
                .block();
    }

    public UserLightDto getUserLightById(Long userId) {
        return authWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("api/v1/users/")
                        .path(userId.toString())
                        .build())
                .retrieve()
                .bodyToMono(UserLightDto.class)
                .doOnError(e -> log.info("Ошибка при получении короткой информации о пользователе ", e))
                .block();
    }

    public List<UserLightDto> getAllExecutors(){
        return authWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("api/v1/users")
                        .queryParam("role", "ROLE_EXECUTOR")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserLightDto>>() {})
                .doOnError(e -> log.info("Ошибка при получении информации о всех исполнителях"))
                .block();
    }
}
