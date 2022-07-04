package com.sanjati.core.services;

import com.sanjati.api.auth.ExecutorDto;
import com.sanjati.api.auth.UserDto;
import com.sanjati.core.entities.Order;
import com.sanjati.core.integrations.AuthServiceIntegration;
import com.sanjati.core.repositories.ProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final ProcessRepository processRepository;
    private final AuthServiceIntegration authServiceIntegration;

    public List<Order> getAllExecutorsOrders(String username){
        ExecutorDto executor = authServiceIntegration.getExecutorDto(username);
        return processRepository.findAllExecutorsOrdersById(executor.getId());
    }
}
