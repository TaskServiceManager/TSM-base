package com.sanjati.core.controllers;

import com.sanjati.api.core.CreationTaskDtoRq;
import com.sanjati.api.core.TaskDtoRs;

import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.converters.TaskConverter;
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
@Tag(name = "Задачи", description = " Методы работы с заявками")
public class TaskController {
    private final TaskService taskService;
    private final TaskConverter taskConverter;

    @Operation(
            summary = "Запрос на создание новой задачи",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Parameter(description = "ID пользователя", required = true)@RequestHeader Long id ,

                           @RequestBody CreationTaskDtoRq taskCreateDto) {
        taskService.createTask(id,taskCreateDto);
    }



    @Operation(
            summary = "Запрос на получение всех заявок пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @GetMapping
    public Page<TaskDtoRs> getAllCurrentUserTasksBySpec(@Parameter(description = "ID автора заявки", required = true)
                                                            @RequestHeader Long id,
                                                        @Parameter(description = "номер страницы", required = true)
                                                            @RequestParam Integer page,
                                                        @Parameter(description = "Граница по времени ОТ. Пример '2022-23-23T00:00'.", required = false)
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            @RequestParam(required = false) LocalDateTime from,
                                                        @Parameter(description = "Граница по времени ДО. Пример '2022-23-23T00:00'." , required = false)
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            @RequestParam(required = false) LocalDateTime to) {
        if (page < 1) {
            page = 1;
        }
        return taskService.findAllTasksBySpec(id,from,to,page).map(taskConverter::entityToDto);
    }

    @Operation(
            summary = "Запрос на получение задач по идентификатору (id)",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{taskId}")
    public TaskDtoRs getTaskById(@PathVariable Long taskId, @RequestHeader String role, @RequestHeader Long userId) {
        if(!role.contains("EXECUTOR")){
            if(!taskService.checkTaskOwnerId(userId,taskId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Нет доступа к чужим заявкам");
        }
        return taskConverter.entityToDto(taskService.findById(taskId));
    }



    @Operation(
            summary = "Запрос на получение всех назначенных на исполнителя заявок по его ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TaskDtoRs.class))
                    )
            }
    )
    @GetMapping("/assigned")
    public Page<TaskDtoRs> getAllAssignedTasksByExecutorId(@Parameter(description = "ID исполнителя", required = true)
                                                               @RequestHeader Long id,
                                                           @Parameter(description = "номер страницы", required = true)
                                                               @RequestParam Integer page,
                                                           @Parameter(description = "Граница по времени ОТ. Пример '2022-23-23T00:00'.", required = false)
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                               @RequestParam(required = false) LocalDateTime from,
                                                           @Parameter(description = "Граница по времени ДО. Пример '2022-23-23T00:00'.", required = false)
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                               @RequestParam(required = false) LocalDateTime to,
                                                           @Parameter(description = "Статус заявок", required = false)
                                                               @RequestParam(required = false) String status){
        if (page < 1) {
            page = 1;
        }
        return taskService.findAllTasksBySpec(from,to,page,status, id).map(taskConverter::entityToDto);
    }
    @Operation(
            summary = "Запрос на получение полного списка всех заявок для дальнейших операций над ними",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TaskDtoRs.class))
                    )
            }
    )
    @GetMapping("/incoming")
    public Page<TaskDtoRs> getAllIncomingTasks(@Parameter(description = "ID исполнителя", required = true)
                                                   @RequestHeader Long id,
                                               @Parameter(description = "номер страницы", required = true)
                                                   @RequestParam Integer page,
                                               @Parameter(description = "Граница по времени ОТ. Пример '2022-23-23T00:00'.", required = false)
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                   @RequestParam(required = false) LocalDateTime from,
                                               @Parameter(description = "Граница по времени ДО. Пример '2022-23-23T00:00'.", required = false)
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                   @RequestParam(required = false) LocalDateTime to,
                                               @Parameter(description = "Статус заявок", required = false)
                                                   @RequestParam(required = false) String status){
        //TODO необходимо реализовать
        return Page.empty();
    }

    @Operation(
            summary = "Запрос исполнителя на взятие заявки в работу по ее ID",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PatchMapping("/take/{id}")// patch
    public void takeTask(@Parameter(description = "ID заявки", required = true) @PathVariable(name = "id") Long taskId,
                                   @Parameter(description = "ID исполнителя", required = true) @RequestHeader(name = "id") Long executorId){
        taskService.assignTask(taskId, executorId,executorId);
    }
    @Operation(
            summary = "Запрос на назначение заявки исполнителю от менеджера",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PatchMapping("/set")
    public void assignTask(@Parameter(description = "ID менеджера", required = true) @RequestHeader(name = "id") Long managerId,
                          @Parameter(description = "ID исполнителя", required = true)@RequestParam(required = true) Long executorId,
                          @Parameter(description = "ID задачи", required = true)@RequestParam(required = true) Long taskId){

        taskService.assignTask(taskId, executorId,managerId);
    }

}
