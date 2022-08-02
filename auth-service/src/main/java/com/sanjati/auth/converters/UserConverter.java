package com.sanjati.auth.converters;

import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.auth.UserDto;
import com.sanjati.auth.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDto modelToDto(User user){
        return new UserDto(user.getId(),user.getUsername(),user.getFirstName(),
                user.getLastName(), user.getMiddleName(), user.getEmail(),
                user.getCompany(), user.getCompanyEmail(), user.getWorkPosition(),
                user.getPhone(), user.getOffice(), user.getBuilding());
    }

    public UserLightDto modelToLightDto(User user){
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
}