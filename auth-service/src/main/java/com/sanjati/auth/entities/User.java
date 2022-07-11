package com.sanjati.auth.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
@Schema(description = "Таблица пользователей")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    @Schema(description = "Короткое имя пользователя")
    private String username;

    @Column(name = "password")
    @Schema(description = "Пароль пользователя")
    private String password;

    @Column(name = "first_name")
    @Schema(description = "Имя пользователя")
    private String firstName;

    @Column(name = "last_name")
    @Schema(description = "Фамилия пользователя")
    private String lastName;

    @Column(name = "middle_name")
    @Schema(description = "Отчяечство пользователя")
    private String middleName;


    @Column(name = "email")
    @Schema(description = "E-mail пользователя")
    private String email;

    @Column(name = "company")
    @Schema(description = "Место работы пользователя")
    private String company;
    @Column(name = "company_email")
    @Schema(description = "Рабочая e-mail пользователя")
    private String companyEmail;
    @Column(name = "work_position")
    @Schema(description = "Должность пользователя на работе")
    private String workPosition;

    @Column(name = "phone")
    @Schema(description = "Телефон пользователя")
    private String phone;

    @Column(name = "office")
    @Schema(description = "Номер офиса на работе у пользователя")
    private String office;

    @Column(name = "building")
    @Schema(description = "Номер помещения на работе у пользователя")
    private String building;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}