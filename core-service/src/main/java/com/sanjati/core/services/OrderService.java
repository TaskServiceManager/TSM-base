


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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;



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

    public List<Order> getAllUserOrders(Long id) {
        return  ordersRepository.findAllUserOrdersById(id);
    }
}




