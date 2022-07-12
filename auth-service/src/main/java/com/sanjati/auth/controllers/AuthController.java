package com.sanjati.auth.controllers;


import com.sanjati.api.auth.UserDto;

import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.auth.converters.UserConverter;
import com.sanjati.auth.dto.JwtRequest;
import com.sanjati.auth.dto.JwtResponse;
import com.sanjati.auth.entities.User;
import com.sanjati.auth.services.UserService;
import com.sanjati.auth.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {
    private final UserConverter userConverter;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

        String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/data")
    public UserDto getFullData(@RequestHeader String username){
        User user = userService.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        return userConverter.modelToDto(user);

    }

}
