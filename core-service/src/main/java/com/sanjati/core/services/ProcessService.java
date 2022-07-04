package com.sanjati.core.services;

import com.sanjati.core.entities.Order;
import com.sanjati.core.repositories.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;

    public Page<Order> getAllExecutorsOrders(Long id){
        return processRepository.findAllExecutorsOrdersById(id, PageRequest.of(0, 100));
    }
}
