package com.sanjati.core.controllers;


import com.sanjati.api.core.OrderDetailsDto;
import com.sanjati.api.core.OrderDto;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.converters.OrderConverter;
import com.sanjati.core.services.OrderService;
import lombok.RequiredArgsConstructor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @GetMapping("/getRole")
    public List<String> getRoles(@RequestHeader String username, @RequestHeader String role) {

        ArrayList<String> roles = new ArrayList<>();
        if(role.contains("ROLE_USER")) roles.add("ROLE_USER");
        if(role.contains("ROLE_EXECUTOR")) roles.add("ROLE_EXECUTOR");
        if(role.contains("ROLE_MANAGER")) roles.add("ROLE_MANAGER");
        if(role.contains("ROLE_ADMIN")) roles.add("ROLE_ADMIN");

        return roles;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestHeader String role, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderService.createOrder(username, orderDetailsDto);
    }

    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username, @RequestHeader String role) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id, @RequestHeader String role) {
        return orderConverter.entityToDto(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }
}
