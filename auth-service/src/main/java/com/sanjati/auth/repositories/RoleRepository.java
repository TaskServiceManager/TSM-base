package com.sanjati.auth.repositories;

import com.sanjati.auth.entities.Role;

import com.sanjati.auth.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findAllByNameIn(List<String> statuses);
}