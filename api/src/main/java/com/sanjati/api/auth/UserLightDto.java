package com.sanjati.api.auth;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

@Schema(description = "Короткая информация о пользователе")
public class UserLightDto {
    @Schema(description = "ID пользователя", example = "3")
    private Long id;
    @Schema(description = "Имя пользователя", example = "Дмитрий")
    private String firstName;
    @Schema(description = "Фамилия пользователя", example = "Дмитриев")
    private String lastName;
    @Schema(description = "Отчество пользователя", example = "Дмитриевич")
    private String middleName;
    @Schema(description = "E-mail пользователя", example = "ddd@mail.ru")
    private String email;

    @Schema(description = "Для пользователя - количество оставленных активных задач. " +
            "Для исполнителя - количество выполняемых активных задач.", example = "3")
    private Long amountActiveTasks;

    @Schema(description = "Время начала рабочего дня исполнителя", example = "09:06:00")
    private LocalTime startWorkTime;
    @Schema(description = "Время окончания рабочего дня исполнителя", example = "18:13:00")
    private LocalTime endWorkTime;

    public UserLightDto() {
    }

    public UserLightDto(Long id, String firstName, String lastName, String middleName, String email, Long amountActiveTasks,
                        LocalTime startWorkTime, LocalTime endWorkTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.amountActiveTasks = amountActiveTasks;
        this.startWorkTime = startWorkTime;
        this.endWorkTime = endWorkTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAmountActiveTasks() {
        return amountActiveTasks;
    }

    public void setAmountActiveTasks(Long amountActiveTasks) {
        this.amountActiveTasks = amountActiveTasks;
    }

    public LocalTime getStartWorkTime() {
        return startWorkTime;
    }

    public void setStartWorkTime(LocalTime startWorkTime) {
        this.startWorkTime = startWorkTime;
    }

    public LocalTime getEndWorkTime() {
        return endWorkTime;
    }

    public void setEndWorkTime(LocalTime endWorkTime) {
        this.endWorkTime = endWorkTime;
    }

    public String getShortNameFormatted() {
        return getLastName() + " " + getFirstName().charAt(0) + "." + (getMiddleName()!=null ? " " + getMiddleName().charAt(0) + "." : "");
    }
}
