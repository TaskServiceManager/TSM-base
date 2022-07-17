package com.sanjati.auth.converters;

import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.auth.UserDtoRs;
import com.sanjati.auth.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDtoRs modelToDto(User user){
        return new UserDtoRs(user.getId(),user.getUsername(),user.getFirstName(),
                user.getLastName(), user.getMiddleName(), user.getEmail(),
                user.getWorkPosition(),user.getCompany(),user.getCompanyEmail(), user.getPhone(),
                user.getOffice(), user.getBuilding());
    }

    public UserLightDto modelToLightDto(User user){
        return UserLightDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .email(user.getEmail())
                .build();
    }
}
