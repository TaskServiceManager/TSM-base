package com.sanjati.auth.converters;

import com.sanjati.api.auth.UserDto;
import com.sanjati.auth.entities.User;

public class UserConverter {
    public UserDto modelToDto(User user) {
        return new UserDto(user.getId(),user.getUsername(),user.getFirstName(),
                user.getLastName(), user.getMiddleName(), user.getEmail(),
                user.getWorkPosition(),user.getCompany(),user.getCompanyEmail(), user.getPhone(),
                user.getOffice(), user.getBuilding());
    }
}
