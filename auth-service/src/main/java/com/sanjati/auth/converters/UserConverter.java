package com.sanjati.auth.converters;

import com.sanjati.api.auth.UserLightDto;
import com.sanjati.api.auth.UserDto;
import com.sanjati.auth.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDto modelToDto(User user){
        if(user==null) {
            return null;
        }
        return new UserDto(user.getId(),user.getUsername(),user.getFirstName(),
                user.getLastName(), user.getMiddleName(), user.getEmail(),
                user.getCompany(), user.getCompanyEmail(), user.getWorkPosition(),
                user.getPhone(), user.getOffice(), user.getBuilding());
    }

    public UserLightDto modelToLightDto(User user){
        if(user==null) {
            return null;
        }
        UserLightDto userLightDto = new UserLightDto();
        userLightDto.setId(user.getId());
        userLightDto.setFirstName(user.getFirstName());
        userLightDto.setLastName(user.getLastName());
        userLightDto.setMiddleName(user.getMiddleName());
        userLightDto.setEmail(user.getEmail());
        userLightDto.setStartWorkTime(user.getStartWorkTime());
        userLightDto.setEndWorkTime(user.getEndWorkTime());
        return userLightDto;
    }
}