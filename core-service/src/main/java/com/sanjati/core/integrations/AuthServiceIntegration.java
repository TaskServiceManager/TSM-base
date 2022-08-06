package com.sanjati.core.integrations;


import com.sanjati.api.auth.UserDto;



import com.sanjati.api.auth.UserLightDto;

import com.sanjati.core.exceptions.AuthServiceIntegrationException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceIntegration {
    private final WebClient authWebClient;

    private final String DATA_PATH = "api/v1/users/{id}/data";
    private final String USER_PATH = "api/v1/users/{id}";
    private final String ALL_USERS_PATH = "api/v1/users";


    public UserDto getUserById(Long userId) {
        return authWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(DATA_PATH)
                        .build(userId))
                .retrieve()
                .bodyToMono(UserDto.class)
                .doOnError(e -> {
                    throw new AuthServiceIntegrationException("Ошибка при получении полной информации о пользователе в модуле auth-service user ID : "+userId);
                })
                .block();
    }

    public UserLightDto getUserLightById(Long userId) {
        return authWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(USER_PATH)
                        .build(userId))
                .retrieve()
                .bodyToMono(UserLightDto.class)
                .doOnError(e -> {
                    throw new AuthServiceIntegrationException("Ошибка при получении короткой информации о пользователе в модуле auth-service user ID : "+userId);
                })
                .block();
    }

    public List<UserLightDto> getUserLightListByIds(Set<Long> userIds) {
        return authWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(ALL_USERS_PATH)
                        .build())
                .body(Mono.just(userIds), ParameterizedTypeReference.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserLightDto>>() {})
                .doOnError(e -> {
                    throw new AuthServiceIntegrationException("Ошибка при получении короткой информации о пользователях в модуле auth-service");
                })
                .block();
    }

    public List<UserLightDto> getAllUsersByRole(String roleName){
        return authWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("api/v1/users")
                        .queryParam("role", roleName)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserLightDto>>() {})
                .doOnError(e -> {
                    throw new AuthServiceIntegrationException("Ошибка при получении информации о пользователях с ролью " + roleName);
                })
                .block();
    }
}
