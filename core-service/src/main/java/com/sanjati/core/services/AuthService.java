package com.sanjati.core.services;

import com.sanjati.api.auth.UserDto;
import com.sanjati.api.auth.UserLightDto;
import com.sanjati.core.integrations.AuthServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<UserLightDto> getAllExecutors(){
        return authServiceIntegration.getAllExecutors();
    }
}
