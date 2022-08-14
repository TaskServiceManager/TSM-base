package com.sanjati.auth.services;


import com.sanjati.api.auth.NewUserDtoRq;
import com.sanjati.api.auth.UserTinyDto;
import com.sanjati.auth.converters.UserConverter;
import com.sanjati.auth.entities.Role;
import com.sanjati.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdministrationService {
    private final RoleService roleService;
    private final UserService userService;
    private final UserConverter userConverter;

    @Transactional
    public void changeRoles(List<String> roles, Long userId){
        if(roles==null) {
            return;
        }
        User user = userService.findByUserId(userId);
        if(user.getRoles()==null) {
            user.setRoles(new ArrayList<>());
        }
        user.getRoles().clear();

        roles.forEach(role -> user.getRoles().add(roleService.findByName(role)));
    }

    @Transactional
    public void changeUserData(NewUserDtoRq update){
        User old = userService.findByUserId(update.getId());
        if(update.getUsername()!=null){
            old.setUsername(update.getUsername());
        }
        if(update.getPassword()!=null){
            old.setPassword(update.getPassword());
        }
        if(update.getFirstName()!=null){
            old.setFirstName(update.getFirstName());
        }
        if(update.getLastName()!=null){
            old.setLastName(update.getLastName());
        }
        if(update.getMiddleName()!=null){
            old.setMiddleName(update.getMiddleName());
        }
        if(update.getEmail()!=null){
            old.setEmail(update.getEmail());
        }
        if(update.getCompany()!=null){
            old.setCompany(update.getCompany());
        }
        if(update.getCompanyEmail()!=null){
            old.setCompanyEmail(update.getCompanyEmail());
        }
        if(update.getWorkPosition()!=null){
            old.setWorkPosition(update.getWorkPosition());
        }
        if(update.getPhone()!=null){
            old.setPhone(update.getPhone());
        }
        if(update.getOffice()!=null){
            old.setOffice(update.getOffice());
        }
        if(update.getBuilding()!=null){
            old.setBuilding(update.getBuilding());
        }

    }

    public Page<UserTinyDto> findUsersBySpec(Long id, String usernamePart, String roleName, Integer page) {
        return userService.findUsersBySpec(id, usernamePart, roleName, page).map(entity->{
            String fio = String.format("%s %c.%c.",entity.getLastName(),entity.getFirstName().charAt(0),entity.getMiddleName().charAt(0));
            List<String> roles = entity.getRoles().stream().map(Role::getName).collect(Collectors.toList());
            return userConverter.entityToTinyDto(entity, fio, roles);
        });

    }
}
