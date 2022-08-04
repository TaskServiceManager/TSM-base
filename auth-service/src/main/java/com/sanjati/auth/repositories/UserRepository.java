package com.sanjati.auth.repositories;


import com.sanjati.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u from User u join fetch u.roles ur where ur.name = :roleName")
    List<User> getAllByRoleName(String roleName);

    List<User> findAllUsersByIdIn(List<Long> id);
}
