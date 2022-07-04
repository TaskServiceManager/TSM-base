package com.sanjati.core.services;


import com.sanjati.api.core.OrderDetailsDto;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.Order;
import com.sanjati.core.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final ProcessService processService;


    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {

    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }
    @Transactional
    public void changeStatus(Long id, String status){
        Order order = ordersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        order.setStatus(status);

    }

    @Transactional
    public void takeOrder(Long orderId, Long executorId) {
        Order order = findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
        order.setStatus("accepted");
        processService.createProcess(order, executorId);
    }
}
