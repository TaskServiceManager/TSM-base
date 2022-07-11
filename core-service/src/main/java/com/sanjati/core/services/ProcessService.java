package com.sanjati.core.services;


import com.sanjati.api.auth.UserDto;
import com.sanjati.core.entities.Order;
import com.sanjati.core.entities.Process;
import com.sanjati.core.repositories.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;

    public Page<Order> getAllExecutorsOrders(Long id){
        return processRepository.findAllExecutorsOrdersById(id, PageRequest.of(0, 100));
    }

    public Page<Order> getAllManagerOrders(Long id){
        return processRepository.findAllManagerOrdersById(id, PageRequest.of(0,100));
    }



    public void createProcess(Order order, Long id){
        Process process = new Process();
        process.setOrder(order);
        process.setIsActive(true);
        process.setOnConfirm(true);
        process.setExecutorId(id);
        //TODO добавить запрос на получение shortName и longName у auth-service
        process.setExecutorShortName("executor.getShortName()");
        process.setManagerShortName("manager.getShortName()");
        process.setExecutorLongName("executor.getLongName()");
        process.setManagerLongName("manager.getLongName()");
        process.setTask(order.getDescription());
        process.setStatus("take_to_work");
        processRepository.save(process);
    }
}
