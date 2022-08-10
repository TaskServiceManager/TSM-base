package com.sanjati.auth.services;

import com.sanjati.api.auth.NewUserDtoRq;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.auth.converters.UserConverter;
import com.sanjati.auth.entities.Role;
import com.sanjati.auth.entities.User;
import com.sanjati.auth.repositories.RoleRepository;
import com.sanjati.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional
    public void createNewUser(NewUserDtoRq newUserDto){

        User user = userConverter.dtoToEntity(newUserDto);

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(()->new ResourceNotFoundException("Роль USER не найдена"));
        List<Role> role = new ArrayList<>();
        role.add(userRole);
        user.setRoles(role);

        user.setStartWorkTime(LocalTime.of(9, 0));
        user.setEndWorkTime(LocalTime.of(18, 0));

        userRepository.save(user);
    }
}
