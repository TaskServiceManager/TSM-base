package com.sanjati.auth.converters;

import com.sanjati.api.auth.NewUserDtoRq;
import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.auth.UserDto;
import com.sanjati.auth.configs.SecurityConfig;
import com.sanjati.auth.entities.Role;
import com.sanjati.auth.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserConverter {
    @Autowired
    SecurityConfig securityConfig;
    public UserDto modelToDto(User user){
        if(user==null) {
            return null;
        }
        return new UserDto(user.getId(),user.getUsername(),user.getFirstName(),
                user.getLastName(), user.getMiddleName(), user.getEmail(),
                user.getCompany(), user.getCompanyEmail(), user.getWorkPosition(),
                user.getPhone(), user.getOffice(), user.getBuilding(),
                user.getStartWorkTime(), user.getEndWorkTime());
    }

    public UserLightDto modelToLightDto(User user){
        if(user==null) {
            return null;
        }
        return UserLightDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .email(user.getEmail())
                .startWorkTime(user.getStartWorkTime())
                .endWorkTime(user.getEndWorkTime())
                .build();
    }
    public User dtoToEntity(NewUserDtoRq newUserDto){

        return new User(newUserDto.getUsername(), securityConfig.passwordEncoder().encode(newUserDto.getPassword()),
                newUserDto.getFirstName(),newUserDto.getLastName(),
                newUserDto.getMiddleName(),newUserDto.getEmail(),
                newUserDto.getCompany(),newUserDto.getCompanyEmail(),
                newUserDto.getWorkPosition(),newUserDto.getPhone(),
                newUserDto.getOffice(),newUserDto.getBuilding());


    }
}