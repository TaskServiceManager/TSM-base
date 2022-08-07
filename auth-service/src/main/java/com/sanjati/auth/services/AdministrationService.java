package com.sanjati.auth.services;


import com.sanjati.api.auth.NewUserDtoRq;
import com.sanjati.api.auth.UserTinyDto;
import com.sanjati.api.exceptions.MandatoryCheckException;
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
    public void changeRole(Integer newRole, Long userId){
        if(newRole<0||newRole>6) throw new MandatoryCheckException("невозможно назначить несуществующие роли");

        List<Long> ids = new ArrayList<>();
        User user = userService.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Пользователь не найден Id: "+userId));

        switch (newRole){
            case 1 :
                ids.add(1L);
                List<Role> userRoles = roleService.findAllByIdIn(ids);
                user.setRoles(userRoles);
                break;

            case 2 :
                ids.add(1L);
                ids.add(2L);
                List<Role> executorRoles = roleService.findAllByIdIn(ids);
                user.setRoles(executorRoles);
                break;

            case 3 :
                ids.add(1L);
                ids.add(2L);
                ids.add(3L);
                List<Role> managerRoles = roleService.findAllByIdIn(ids);
                user.setRoles(managerRoles);
                break;

            case 4 :// можно дополнительно ввести исполнителя админа
                ids.add(1L);
                ids.add(2L);
                ids.add(3L);
                ids.add(4L);
                List<Role> adminManagerRoles = roleService.findAllByIdIn(ids);
                user.setRoles(adminManagerRoles);
                break;

            case 5 :
                ids.add(1L);
                ids.add(5L);
                List<Role> seniorRoles = roleService.findAllByIdIn(ids);
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

        return userService.findUsersBySpec(id,usernamePart,role,page).map(userConverter::entityToTinyDto);

    }
}
