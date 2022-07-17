package com.sanjati.core.controllers;

import com.sanjati.api.core.TimePointDtoRs;
import com.sanjati.core.converters.TimePointConverter;
import com.sanjati.core.services.TimePointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/v1/time")
@RequiredArgsConstructor
@Tag(name = "Учёт времени", description = " Методы работы с отметками по времени")

public class TimePointController {
    private final TimePointService timePointService;
    private final TimePointConverter timePointConverter;
    @Operation(
            summary = "Запрос на изменение статуса отметки времени",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping
    public void createTimePointOrChangeStatus(@Parameter(description = "ID пользователя", required = true)@RequestHeader Long id,
                           @Parameter(description = "ID задачи", required = true)@RequestParam Long taskId,
                           @Parameter(description = "ID временной отметки если надо её закрыть", required = false)@RequestParam Long timePontId) {
        timePointService.changeStatusOrCreateTimePoint(taskId,id,timePontId);
    }
    @GetMapping("/report")
    public Page<TimePointDtoRs> getAllTimePoints(@Parameter(description = "ID исполнителя", required = true)
                                                     @RequestHeader Long id,
                                                 @Parameter(description = "ID задачи", required = false)
                                                 @RequestParam(required = false) Long taskId,
                                                 @Parameter(description = "номер страницы", required = true)
                                                     @RequestParam Integer page,
                                                 @Parameter(description = "Граница по времени ОТ. Пример '2022-23-23T00:00'.", required = false)
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                     @RequestParam(required = false) LocalDateTime from,
                                                 @Parameter(description = "Граница по времени ДО. Пример '2022-23-23T00:00'.", required = false)
                                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                     @RequestParam(required = false) LocalDateTime to){
        return timePointService.getAllTimePointsBySpec(id,taskId,page,from,to).map(timePointConverter::entityToDto);

    }
}
