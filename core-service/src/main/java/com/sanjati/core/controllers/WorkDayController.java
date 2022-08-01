package com.sanjati.core.controllers;

import com.sanjati.api.core.WorkDayDtoRq;
import com.sanjati.core.services.WorkDayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/workdays")
@RequiredArgsConstructor
@Tag(name = "Рабочие дни", description = "Методы работы с рабочими днями")
public class WorkDayController {

    private final WorkDayService workDayService;

    @Operation(
            summary = "Запрос на создание нового рабочего дня",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Некорректный запрос", responseCode = "400"
                    )
            }
    )
    @PostMapping
    public void createWorkDay(@Parameter(description = "ID исполнителя")
                                  @RequestHeader(name = "id") Long executorId,
                              @Parameter(description = "Тело запроса")
                                  @RequestBody WorkDayDtoRq workDay){
        workDayService.createWorkDay(executorId, workDay);
    }

    @Operation(
            summary = "Запрос на изменение окончания текущего рабочего дня",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Некорректный запрос", responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Ресурс не найден", responseCode = "404"
                    )
            }
    )
    @PatchMapping("/end/{newEndWorkDay}")
    public void changeEndWorkDay(@Parameter(description = "ID исполнителя")
                                      @RequestHeader(name = "id") Long executorId,
                                  @Parameter(description = "Новый конец рабочего дня")
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                      @PathVariable LocalDateTime newEndWorkDay){
        workDayService.changeEndWorkDay(executorId, newEndWorkDay);
    }


}
