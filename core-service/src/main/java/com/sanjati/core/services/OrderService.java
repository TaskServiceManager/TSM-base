package com.sanjati.core.services;


import com.sanjati.api.core.OrderDetailsDto;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.Order;
import com.sanjati.core.repositories.OrdersRepository;
import com.sanjati.core.repositories.specifications.OrderSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.function.Failable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final ProcessService processService;


    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {

    }

    public List<Order> findOrdersByUsername(String username) {
        Specification<Order> spec = Specification.where(null);
        spec = spec.and(OrderSpecifications.usernameEquals(username));
        return ordersRepository.findAll(spec);
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

    public Page<Order> findOrdersById(Long id, String from, String to,Integer page) {
        Specification<Order> spec = Specification.where(null);
        spec = spec.and(OrderSpecifications.idEquals(id));

        LocalDateTime newDateFormat;
        if (from != null) {
            newDateFormat = LocalDateTime.parse(from.substring(0, 22));
            spec = spec.and(OrderSpecifications.timeGreaterOrEqualsThan(newDateFormat));
            log.warn(from);
        }

        if (to != null) {
            newDateFormat = LocalDateTime.parse(to.substring(0, 22));
            log.warn(to);
            spec = spec.and(OrderSpecifications.timeLessThanOrEqualsThan(newDateFormat));
        }

        return this.ordersRepository.findAll(spec, PageRequest.of(page - 1, 10));
    }

  // ребят используем спеки findAll(spec)
   // public List<Order> getAllUserOrders(Long id) {
   //    return  ordersRepository.findAllUserOrdersById(id);
   // }

   // public List<Order> getAllManagerOrders(Long id) {
   //     return  ordersRepository.findAllManagerOrdersById(id);
   // }


}
