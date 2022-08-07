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

    public List<Role> findAllByIdIn(List<Long> ids){
       return roleRepository.findAllById(ids);
    }
    public Role findById(Long id){
        return roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Роль не найдена Id : "+ id));
    }
}
