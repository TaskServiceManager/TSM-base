package com.sanjati.auth.services;


import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.auth.converters.UserConverter;
import com.sanjati.auth.entities.Role;
import com.sanjati.auth.entities.User;
import com.sanjati.auth.repositories.RoleRepository;
import com.sanjati.auth.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserConverter userConverter;


    /*
    *   Поиск имени пользователя
    * */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    /*
    *  Загрузка пользователя по имени
    * */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)  {
        User user = findByUsername(username).orElseThrow(() -> new ResourceNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }


    /*
    *  Загрузка списка ролей
    * */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }



    /*
    *  Поиск пользователя по ID
    * */
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    //если будет много парамов, то потом надо переделать на спеку
    public List<User> getAllUsers(String roleName){
        if (roleName != null){
            Role role = roleRepository.findByName(roleName).orElseThrow(() -> new ResourceNotFoundException(String.format("Указанная роль %s не найдена.",roleName)));
            return userRepository.findAllByRoles(role);
        }
        return userRepository.findAll();
    }

    /*
    * составление List<UserLightDto> по списку ID
    *  */
    public List<UserLightDto> getLightUserDataById(List<Long> usersId) {
        List<UserLightDto> lightUsers = new ArrayList<>();
        for (Long userId : usersId) {
            User user = findByUserId(userId).get();
            lightUsers.add(userConverter.modelToLightDto(user));
        }
        return lightUsers;
    }

}