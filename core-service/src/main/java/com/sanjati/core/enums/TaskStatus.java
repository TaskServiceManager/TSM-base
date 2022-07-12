package com.sanjati.core.enums;

public enum TaskStatus {
    CREATED("Создана"),
    CANCELLED("Отменена"),
    ASSIGNED("Назначена"),
    ACCEPTED("В работе"),
    DELAYED("Отложена"),
    COMPLETED("Выполнена");

    private final String rus;
    TaskStatus(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }
}
