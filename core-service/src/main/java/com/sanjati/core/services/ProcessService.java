package com.sanjati.core.services;

import com.sanjati.core.entities.Order;
import com.sanjati.core.repositories.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;

    public List<Order> getAllExecutorsOrders(Long id){
        return processRepository.findAllExecutorsOrdersById(id);
    }
}
