package com.sanjati.core.exceptions;

import com.sanjati.api.exceptions.AppError;
import com.sanjati.api.exceptions.MandatoryCheckException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ChangeTaskStatusException e){
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.toString(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<AppError> catchAuthServiceIntegrationException(AuthServiceIntegrationException e){
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(new AppError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    public ResponseEntity<AppError> catchMandatoryCheckException(MandatoryCheckException e){
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(new AppError(HttpStatus.UNPROCESSABLE_ENTITY.toString(), e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
