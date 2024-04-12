package com.bci.apiusuario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        System.out.println(ex.getClass());
        System.out.println(ex.getLocalizedMessage());
        BindingResult result = ex.getBindingResult();
        String messageError = "";
        for (FieldError error : result.getFieldErrors()) {
            messageError = messageError.concat(error.getDefaultMessage()).concat(". ");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageException.Message(HttpStatus.BAD_REQUEST, messageError));
    }
}
