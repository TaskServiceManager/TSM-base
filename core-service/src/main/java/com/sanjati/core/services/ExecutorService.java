package com.sanjati.core.services;

import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.core.entities.Executor;
import com.sanjati.core.repositories.ExecutorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExecutorService {
    private final ExecutorRepository executorRepository;

    public Executor findExecutorByExecutorId(Long id){
        return executorRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Executor not found"));
    }
}
