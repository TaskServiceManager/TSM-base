package com.sanjati.core.services;

import com.sanjati.api.auth.UserDto;
import com.sanjati.api.auth.UserLightDto;
import com.sanjati.core.integrations.AuthServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthServiceIntegration authServiceIntegration;

    public UserDto getUserById(Long userId){
        return authServiceIntegration.getUserById(userId);
    }

    public UserLightDto getUserLightById(Long userId){
        return authServiceIntegration.getUserLightById(userId);
    }

    public List<UserLightDto> getAllUsersByRole(String role){
        return authServiceIntegration.getAllUsersByRole(role);
    }

    public List<UserLightDto> getUserLightListByIds(Set<Long> usersIds){
        return authServiceIntegration.getUserLightListByIds(usersIds);
    }
}
