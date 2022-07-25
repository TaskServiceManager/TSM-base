package com.sanjati.core.controllers;

import com.sanjati.api.core.TaskDtoRq;
import com.sanjati.api.core.TaskDto;
import com.sanjati.core.converters.TaskConverter;
import com.sanjati.core.enums.TaskStatus;
import com.sanjati.core.services.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Заявки", description = "Методы работы с заявками")
public class TaskController {
    private final TaskService taskService;
    private final TaskConverter taskConverter;

    @Operation(
            summary = "Запрос на создание новой заявки",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Parameter(description = "ID пользователя", required = true)@RequestHeader Long id,
                           @Parameter(description = "Тело запроса", required = true)@RequestBody TaskDtoRq taskCreateDto) {
        taskService.createTask(id,taskCreateDto);
    }

    @Operation(
            summary = "Запрос на получение всех заявок удовлетворяющих условиям",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @GetMapping
    public Page<TaskDto> getAllTasksBySpec(@Parameter(description = "ID автора", required = false)
                                                @RequestParam(required = false) Long ownerId,
                                           @Parameter(description = "номер страницы", required = true)
                                                @RequestParam Integer page,
                                           @Parameter(description = "Граница по времени ОТ. Пример '2022-23-23T00:00'.", required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                @RequestParam(required = false) LocalDateTime from,
                                           @Parameter(description = "Граница по времени ДО. Пример '2022-23-23T00:00'." , required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                @RequestParam(required = false) LocalDateTime to,
                                           @Parameter(description = "Статус заявок", required = false, example = "Создана")
                                                @RequestParam(required = false) String status,
                                           @Parameter(description = "ID исполнителя", required = false)
                                                @RequestParam(required = false) Long executorId) {
        if (page < 1) {
            page = 1;
        }
        return taskService.findAllTasksBySpec(ownerId,from,to,page,status,executorId).map(taskConverter::entityToDto);
    }

    @Operation(
            summary = "Запрос на получение заявки по идентификатору (id)",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{id}")
    public TaskDto getTaskById(@Parameter(description = "ID заявки", required = true)
                                   @PathVariable Long id,
                               @Parameter(description = "Роль пользователя", required = true)
                                   @RequestHeader String role,
                               @Parameter(description = "ID пользователя", required = true)
                                   @RequestHeader(name = "id") Long userId) {
        if(!role.contains("EXECUTOR")){
            if(!taskService.checkTaskOwnerId(userId,id)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нет доступа к чужим заявкам");
        }
        return taskConverter.entityToDto(taskService.findById(id));
    }

    @Operation(
            summary = "Запрос на изменение статуса заявки по ее ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PatchMapping("/{id}/status/{newStatus}")
    public void changeTaskStatus(@Parameter(description = "ID заявки", required = true)
                                     @PathVariable Long id,
                                 @Parameter(description = "Новый статус заявки", required = true)

                                     @PathVariable String newStatus,

                                 @Parameter(description = "ID исполнителя", required = true)
                                     @RequestHeader(name = "id") Long executorId){
        taskService.changeStatus(id, newStatus);
    }

    @Operation(
            summary = "Запрос на назначение заявки.",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/assign/{id}")
    public void assignTask(@Parameter(description = "ID заявки", required = true)
                               @PathVariable Long id,
                           @Parameter(description = "ID назначающего", required = true)
                               @RequestHeader(name = "id") Long assignerId,
                           @Parameter(description = "ID исполнителя")
                               @RequestParam(required = false) Long executorId
                           ){
        taskService.assignTask(id, assignerId, executorId);
    }

}
