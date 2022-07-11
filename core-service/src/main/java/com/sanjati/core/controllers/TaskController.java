package com.sanjati.core.controllers;

import com.sanjati.api.core.CreationTaskDto;
import com.sanjati.api.core.TaskDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public void createTask(@RequestHeader String username,  @RequestBody CreationTaskDto taskCreateDto) {
        taskService.createTask(username, taskCreateDto);
    }

//    @Operation(
//            summary = "Запрос на получение текущего заказа пользователя", ---> не применимо!
//            responses = {
//                    @ApiResponse(
//                            description = "Успешный ответ", responseCode = "200"
//                    )
//            }
//    )
//    @GetMapping
//    public List<TaskDto> getCurrentUserOrders(@RequestHeader String username, @RequestHeader String role) {
//        return orderService.findOrdersByUsername(username).stream()
//                .map(orderConverter::entityToDto).collect(Collectors.toList());
//    }

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
    public Page<TaskDto> getCurrentUserTasksBySpec(@Parameter(description = "ID исполнителя", required = true)
                                                        @RequestHeader Long id,
                                                   @Parameter(description = "номер страницы", required = true)
                                                        @RequestParam Integer page,
                                                   @Parameter(description = "Граница по времени ОТ", required = false)
                                                        @RequestParam(required = false) String from,
                                                   @Parameter(description = "Граница по времени ДО", required = false)
                                                        @RequestParam(required = false) String to) {
        if (page < 1) {
            page = 1;
        }
        return taskService.findTasksByUserId(id,from,to,page).map(taskConverter::entityToDto);
    }

    @Operation(
            summary = "Запрос на получение задач по идентификатору (id)",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{uid}")
    public TaskDto getTaskById(@PathVariable Long uid, @RequestHeader String role, @RequestHeader String id) {
        return taskConverter.entityToDto(taskService.findById(uid).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }

//    @Operation(
//            summary = "Запрос на получение всех заявок пользователя, которые он оставил",
//            responses = {
//                    @ApiResponse(
//                            description = "Успешный ответ", responseCode = "200"
//                    )
//            }
//    )
//    @GetMapping("/my")
//    public Page<TaskDto> getMyTasks (@Parameter(description = "ID пользователя", required = true) @RequestHeader Long id,
//                                     @Parameter(description = "Граница по времени ОТ", required = false) @RequestParam(required = false) String from,
//                                     @Parameter(description = "Граница по времени ДО", required = false) @RequestParam(required = false) String to) {
//        return taskService.findTasksByUserId(id,from,to,1).map(taskConverter::entityToDto);
//
//    }

    @Operation(
            summary = "Запрос на получение всех заявок исполнителя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TaskDto.class))
                    )
            }
    )
    @GetMapping("/assigned")
    public Page<TaskDto> getAssignedTasks(@Parameter(description = "ID исполнителя", required = true)
                                                @RequestHeader Long id,
                                          @Parameter(description = "номер страницы", required = true)
                                                @RequestParam Integer page,
                                          @Parameter(description = "Граница по времени ОТ", required = false)
                                                @RequestParam(required = false) String from,
                                          @Parameter(description = "Граница по времени ДО", required = false)
                                                @RequestParam(required = false) String to,
                                          @Parameter(description = "Статус заявок", required = false)
                                                @RequestParam(required = false) String status){
        if (page < 1) {
            page = 1;
        }
        return taskService.getAllAssignedTasks(id,from,to,page,status).map(taskConverter::entityToDto);
    }

    @GetMapping("/incoming")
    public Page<TaskDto> getIncomingTasks(){
        //TODO необходимо реализовать
        return Page.empty();
    }

    @Operation(
            summary = "Запрос на взятие заявки исполнителем",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PatchMapping("/take/{id}")
    public void takeTask(@Parameter(description = "ID заявки", required = true) @PathVariable(name = "id") Long orderId,
                                   @Parameter(description = "ID исполнителя", required = true) @RequestHeader(name = "id") Long executorId){
        taskService.takeTask(orderId, executorId);
    }
}
