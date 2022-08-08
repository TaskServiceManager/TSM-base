package com.sanjati.auth.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;


    @Column(name = "email")
    private String email;

    @Column(name = "company")
    private String company;
    @Column(name = "company_email")
    private String companyEmail;
    @Column(name = "work_position")
    private String workPosition;

    @Column(name = "phone")
    private String phone;

    @Column(name = "office")
    private String office;

    @Column(name = "building")
    private String building;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Column(name = "start_work_time")
    private LocalTime startWorkTime;
    @Column(name = "end_work_time")
    private LocalTime endWorkTime;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User(String username, String password, String firstName, String lastName, String middleName, String email, String company, String companyEmail, String workPosition, String phone, String office, String building) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.company = company;
        this.companyEmail = companyEmail;
        this.workPosition = workPosition;
        this.phone = phone;
        this.office = office;
        this.building = building;

    }

    public User() {
    }
}