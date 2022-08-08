package com.sanjati.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

@Schema(description = "Очень короткая информация о пользователе")

public class NewUserDtoRq {
    @Schema(description = "Короткое имя пользователя", example = "userHi")
    private String username;

    @Schema(description = "Пароль", example = "1234567QWERTY")
    private String password;

    @Schema(description = "Имя пользователя", example = "Дмитрий")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Дмитриев")
    private String lastName;


    @Schema(description = "Отчество пользователя", example = "Дмитриевич")

    private String middleName;

    @Schema(description = "E-mail пользователя", example = "ddd@mail.ru")
    private String email;

    @Schema(description = "Место работы пользователя", example = "фирма 'Работяги'")
    private String company;

    @Schema(description = "Рабочая e-mail пользователя", example = "ddd@worlWorrers.ru")
    private String companyEmail;

    @Schema(description = "Должность пользователя на работе", example = "главный инженер")
    private String workPosition;

    @Schema(description = "Телефон пользователя", example = "+7(978) 123-45-67")
    private String phone;

    @Schema(description = "Номер офиса на работе у пользователя", example = "33")
    private String office;

    @Schema(description = "Номер помещения на работе у пользователя", example = "3")
    private String building;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public void setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public NewUserDtoRq(String username, String password, String firstName,
                        String lastName, String middleName, String email,
                        String company, String companyEmail, String workPosition,
                        String phone, String office, String building){
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

    public NewUserDtoRq() {
    }

}

