package com.sanjati.core.integrations;


import com.sanjati.api.auth.UserDto;

import com.sanjati.api.exceptions.AuthAppError;
import com.sanjati.core.exceptions.AuthServiceIntegrationException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AuthServiceIntegration {
    private final WebClient cartServiceWebClient;


    public UserDto getUser(String username) {
        UserDto user = cartServiceWebClient.get()
                .uri("/api/v1/data")
                .header("username", username)
                // .bodyValue(body) // for POST
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(AuthAppError.class).map(
                                body -> {
                                    if (body.getCode().equals(AuthAppError.CartServiceErrors.USER_NOT_FOUND.name())) {
                                        return new AuthServiceIntegrationException("Пользователь  найден");
                                    }

                                    return new AuthServiceIntegrationException("Выполнен некорректный запрос к сервису : причина неизвестна");
                                }
                        )
                )
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new CartServiceIntegrationException("Выполнен некорректный запрос к сервису ")))
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CartServiceIntegrationException("Сервис сломался")))
                .bodyToMono(UserDto.class)
                .block();
        return user;
    }
}
