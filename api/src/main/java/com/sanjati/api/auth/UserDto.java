package com.sanjati.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserDto {

    @Schema(description = "ID пользователя", example = "3")
    private Long id;

    @Schema(description = "Короткое имя пользователя", example = "userHi")
    private String username;

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

    public UserDto(Long id, String username, String firstName,
                   String lastName, String middleName, String email,
                   String company, String companyEmail, String workPosition,
                   String phone, String office, String building) {
        this.id = id;
        this.username = username;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public UserDto() {
    }
}
