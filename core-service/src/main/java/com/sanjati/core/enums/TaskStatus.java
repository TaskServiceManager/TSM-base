package com.sanjati.core.enums;

public enum TaskStatus {
    CREATED("Создана"),
    CANCELLED("Отменена"),
    ASSIGNED("Назначена"),
    ACCEPTED("В работе"),
    APPROVED("Утверждается"),
    DELAYED("Отложена"),
    COMPLETED("Выполнена"),
    NOT_FOUND("Статус не найден");

    private final String rus;
    TaskStatus(String rus) {
        this.rus = rus;
    }

    public static TaskStatus fromRusValue(String rusValue) {
        for (final TaskStatus taskStatus : values()) {
            if (taskStatus.getRus().equalsIgnoreCase(rusValue)) {
                return taskStatus;
            }
        }
        return NOT_FOUND;
    }
    public String getRus() {
        return rus;
    }
}
