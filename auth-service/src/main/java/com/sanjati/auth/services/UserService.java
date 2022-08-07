package com.sanjati.auth.services;

import com.sanjati.api.auth.WorkTimeDtoRq;
import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.auth.converters.UserConverter;
import com.sanjati.auth.entities.Role;
import com.sanjati.auth.entities.User;
import com.sanjati.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final UserConverter userConverter;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)  {
        User user = findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers(String roleName){
        return userRepository.getAllByRoleName(roleName);
    }

    public void updateWorkTime(Long executorId, WorkTimeDtoRq workTimeDtoRq){
        User user = userRepository.findById(executorId).orElseThrow(
                () -> new ResourceNotFoundException("Пользователь с указанный ID не найден"));
        user.setStartWorkTime(workTimeDtoRq.getStartWorkTime());
        user.setEndWorkTime(workTimeDtoRq.getEndWorkTime());
        userRepository.save(user);
    }

    public List<UserLightDto> getLightUserDataById(List<Long> userIds) {
        return userRepository.findAllUsersByIdIn(userIds).stream()
                .map(userConverter::modelToLightDto)
                .collect(Collectors.toList());
    }

}