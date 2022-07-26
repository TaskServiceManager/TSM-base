package com.sanjati.core.controllers;

import com.sanjati.api.auth.UserLightDto;
import com.sanjati.core.services.StatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistic")
@Tag(name = "Статистика", description = "Методы работы со статистикой")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @Operation(
            summary = "Запрос на получение исполнителей с их занятостью",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/employment")
    public List<UserLightDto> test(){
        return statisticService.getAllExecutorsSortedByEmployment();
    }
}
