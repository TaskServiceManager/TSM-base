package com.sanjati.core.controllers;



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
@Tag(name = "Заказы", description = " Методы работы с заказами")
public class TaskController {
    private final TaskService taskService;
    private final TaskConverter taskConverter;

    @Operation(
            summary = "Список ролей",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/getRole")
    public List<String> getRoles(@RequestHeader String username, @RequestHeader String role) {

        ArrayList<String> roles = new ArrayList<>();

        if(role.contains("ROLE_USER")) roles.add("ROLE_USER");
        if(role.contains("ROLE_EXECUTOR")) roles.add("ROLE_EXECUTOR");
        if(role.contains("ROLE_MANAGER")) roles.add("ROLE_MANAGER");
        if(role.contains("ROLE_SENIOR")) roles.add("ROLE_SENIOR");
        if(role.contains("ROLE_ADMIN")) roles.add("ROLE_ADMIN");

        return roles;
    }


    @Operation(
            summary = "Запрос на создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@RequestHeader String username,  @RequestBody TaskDto taskCreateDto) {
        taskService.createTask(username, taskCreateDto);
    }

//    @GetMapping
//    public List<TaskDto> getCurrentUserOrders(@RequestHeader String username, @RequestHeader String role) {
//        return orderService.findOrdersByUsername(username).stream()
//                .map(orderConverter::entityToDto).collect(Collectors.toList());
//    }
    @Operation(
            summary = "Запрос на получение текущего заказа пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping
    public Page<TaskDto> getCurrentUserTaskBySpec(@RequestHeader Long id, @RequestParam Integer page, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        if (page < 1) {
            page = 1;
        }
        return taskService.findTasksByUserId(id,from,to,page).map(taskConverter::entityToDto);
    }

    @Operation(
            summary = "Запрос на получение заказа по идентификатору (id)",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long uid, @RequestHeader String role, @RequestHeader String id) {
        return taskConverter.entityToDto(taskService.findById(uid).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }


    @Operation(
            summary = "Запрос на получение всех заявок пользователя, которые он оставил",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/my")
    public Page<TaskDto> getMyTasks (@Parameter(description = "ID пользователя", required = true) @RequestHeader Long id) {
        return taskService.findTasksByUserId(id,null,null,1).map(taskConverter::entityToDto);

    }


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
    public Page<TaskDto> getAssignedTasks(@Parameter(description = "ID исполнителя", required = true) @RequestHeader Long id,
                                          @Parameter(description = "Номер страницы") @RequestParam(defaultValue = "1") Integer page){
        if (page < 1) {
            page = 1;
        }
        return taskService.getAssignedTaskByExecutorId(id, page).map(taskConverter::entityToDto);
    }

    @Operation(
            summary = "Запрос на получение всех заявок, которые еще не были обработаны",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ",responseCode = "200",
                            content = @Content(schema = @Schema(implementation = TaskDto.class))
                    )
            }
    )
    @GetMapping("/incoming")
    public Page<TaskDto> getIncomingTasks(@Parameter(description = "Номер страницы")@RequestParam Integer page){
        //TODO реализовать логику метода 'getIncomingTasks(Integer page)';
        return taskService.getIncomingTasks(page).map(taskConverter::entityToDto);
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
