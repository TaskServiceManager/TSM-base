package com.sanjati.api.exceptions;

public class AuthAppError extends AppError{
    public enum CoreServiceErrorCode {
        USER_NOT_FOUND, SERVICE_IS_BROKEN
    }
    public AuthAppError(String code, String message) {
        super(code, message);
    }
}
