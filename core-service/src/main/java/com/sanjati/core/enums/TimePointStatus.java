package com.sanjati.core.enums;

public enum TimePointStatus {
    IN_PROCESS("В процессе"),
    FINISHED("Завершена");

    private final String rus;
    TimePointStatus(String rus) {
        this.rus = rus;
    }
    public String getRus() {
        return rus;
    }
}
