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
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = " Методы работы с заказами")
public class TaskController {
    private final TaskService orderService;
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
    public void createOrder(@RequestHeader String username,  @RequestBody TaskCreateDto taskCreateDto) {
        orderService.createOrder(username, taskCreateDto);
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
    public Page<TaskDto> getCurrentUserOrdersBySpec(@RequestHeader Long id, @RequestParam Integer page, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        if (page < 1) {
            page = 1;
        }
        return orderService.findOrdersById(id,from,to,page).map(taskConverter::entityToDto);
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
    public TaskDto getOrderById(@PathVariable Long uid, @RequestHeader String role, @RequestHeader String id) {
        return taskConverter.entityToDto(orderService.findById(uid).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }


    @Operation(

            summary = "Запрос на получение всех заявок пользователя",


            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )

    @GetMapping("/user")
    public List<TaskDto> getUserOrders (@Parameter(description = "ID пользователя", required = true) @RequestHeader Long id) {
        return orderService.findOrdersById(id,null,null,1).stream().map(taskConverter::entityToDto).collect(Collectors.toList());

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
    @GetMapping("/executor")
    public Page<TaskDto> getExecutorsOrders(@Parameter(description = "ID исполнителя", required = true) @RequestHeader Long id){
        return processService.getAllExecutorsOrders(id).map(taskConverter::entityToDto);
    }

    @Operation(
            summary = "Запрос на взятие заявки исполнителем",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("executor/take/{id}")
    public void executorTakesOrder(@Parameter(description = "ID заявки", required = true) @PathVariable(name = "id") Long orderId,
                                   @Parameter(description = "ID исполнителя", required = true) @RequestHeader Long id){
        orderService.takeTask(orderId, id);
    }
}
