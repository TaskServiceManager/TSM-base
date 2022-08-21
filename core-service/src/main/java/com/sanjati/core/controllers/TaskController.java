package com.sanjati.core.controllers;

import com.sanjati.api.core.AssignDtoRq;
import com.sanjati.api.core.SearchParamsTaskDtoRq;
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
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Slf4j
@RestController

@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Заявки", description = "Методы работы с заявками")
@Validated
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
                           @Parameter(description = "Тело запроса", required = true)@RequestBody @Valid @NotNull TaskDtoRq taskCreateDto) {
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
    @PostMapping("/search")
    public Page<TaskDto> getAllTasksBySpec(@Parameter(description = "Тело запроса с параметрами поиска", required = false)
                                                @RequestBody @Valid SearchParamsTaskDtoRq searchParams) {

        return taskService.findAllTasksBySpec(searchParams).map(taskConverter::entityToDto);
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
        taskService.checkAccessToTask(role, userId, id);
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
                                 @Parameter(description = "ID текущего пользователя")
                                    @RequestHeader(name = "id") Long userId,
                                 @Parameter(description = "Новый статус заявки", required = true)
                                     @PathVariable TaskStatus newStatus,
                                 @Parameter(description = "Комментарий сотрудника", required = false)
                                     @RequestParam(required = false) String comment){
        taskService.changeStatus(id, userId, newStatus, comment);
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

    @Operation(
            summary = "Запрос на назначение заявки на группу пользователей",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping("/assign/{id}")
    public void assignTaskBatch(@Parameter(description = "ID заявки", required = true)
                                @PathVariable Long id,
                                @Parameter(description = "ID назначающего", required = true)
                                @RequestHeader(name = "id") Long assignerId,
                                @Parameter(description = "Тело запроса", required = true)
                                @RequestBody @Valid @NotNull AssignDtoRq assignDtoRq
    ){
        log.warn("____________________________ZAPROS____________________________");
        taskService.assignTaskBatch(id, assignerId, assignDtoRq);
    }

}
