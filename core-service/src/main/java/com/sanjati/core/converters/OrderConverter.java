package com.sanjati.core.converters;

import com.sanjati.api.core.OrderDto;
import com.sanjati.core.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    //TODO конвертер
    public OrderDto entityToDto(Order order) {
        return new OrderDto(order.getId(), order.getTitle(),order.getDescription(),order.getStatus(),order.getCompletedAt(),order.getCreatedAt(),order.getUpdatedAt());

    }
}
