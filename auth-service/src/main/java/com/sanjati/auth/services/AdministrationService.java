package com.sanjati.auth.services;


import com.sanjati.api.auth.NewUserDtoRq;
import com.sanjati.api.auth.UserTinyDto;
import com.sanjati.api.exceptions.ResourceNotFoundException;
import com.sanjati.auth.converters.UserConverter;
import com.sanjati.auth.entities.Role;
import com.sanjati.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministrationService {
    private final RoleService roleService;
    private final UserService userService;
    private final UserConverter userConverter;
    @Transactional
    public void changeRole(String newRole, Long userId){


        List<String> roles = new ArrayList<>();
        User user = userService.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Пользователь не найден Id: "+userId));

        switch (newRole){
            case "USER" :
                roles.add("ROLE_USER");
                List<Role> userRoles = roleService.findAllByNameIn(roles);
                user.setRoles(userRoles);
                break;

            case "EXECUTOR":
                roles.add("ROLE_USER");
                roles.add("ROLE_EXECUTOR");
                List<Role> executorRoles = roleService.findAllByNameIn(roles);
                user.setRoles(executorRoles);
                break;

            case "MANAGER":
                roles.add("ROLE_USER");
                roles.add("ROLE_EXECUTOR");
                roles.add("ROLE_MANAGER");
                List<Role> managerRoles = roleService.findAllByNameIn(roles);
                user.setRoles(managerRoles);
                break;

            case "ADMIN":// можно дополнительно ввести исполнителя админа
                roles.add("ROLE_USER");
                roles.add("ROLE_EXECUTOR");
                roles.add("ROLE_MANAGER");
                roles.add("ROLE_ADMIN");
                List<Role> adminManagerRoles = roleService.findAllByNameIn(roles);
                user.setRoles(adminManagerRoles);
                break;

            case "SENIOR":
                roles.add("ROLE_USER");
                roles.add("ROLE_SENIOR");
                List<Role> seniorRoles = roleService.findAllByNameIn(roles);
                user.setRoles(seniorRoles);
                break;



        }


    }
    @Transactional
    public void changeUserData(NewUserDtoRq update,Long userId){
        User old = userService.findByUserId(userId).orElseThrow(()-> new ResourceNotFoundException("Пользователь не найден Id: "+userId));
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

    public Page<UserTinyDto> findUsersBySpec(Long id, String usernamePart, Long role, Integer page) {

        return userService.findUsersBySpec(id,usernamePart,role,page).map(entity->{
            String fio = String.format("%s %c.%c.",entity.getFirstName(),entity.getLastName().charAt(0),entity.getMiddleName().charAt(0));
            return userConverter.entityToTinyDto(entity,fio);
        });

    }
}
