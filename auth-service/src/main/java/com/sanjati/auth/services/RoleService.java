package com.sanjati.auth.services;

import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.auth.entities.Role;
import com.sanjati.auth.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByName(String name){
        return roleRepository.findByName(name).orElseThrow(()->new ResourceNotFoundException("Не найдена роль "+ name));
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
