package com.sanjati.auth.repositories;


import com.sanjati.auth.entities.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Schema(description = "Поиск пользователя по имени")
    Optional<User> findByUsername(String username);
}
