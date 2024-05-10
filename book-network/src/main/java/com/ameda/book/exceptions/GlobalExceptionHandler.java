package com.ameda.book.exceptions;/*
*
@author ameda
@project Books
*
*/

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_LOCKED.getCode())
                                .businessExceptionDescription(BusinessErrorCodes.ACCOUNT_LOCKED.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.ACCOUNT_DISABLED.getCode())
                                .businessExceptionDescription(BusinessErrorCodes.ACCOUNT_DISABLED.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException ex){
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BusinessErrorCodes.BAD_CREDENTIALS.getCode())
                                .businessExceptionDescription(BusinessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .error(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException msgEx){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error(msgEx.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex){
        Set<String> errors = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
        });
        return ResponseEntity.status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .validationErrors(errors)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessExceptionDescription("internal error, reach out to admin")
                                .error(ex.getMessage())
                                .build()
                );
    }
    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException ex){
        return ResponseEntity.status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .error(ex.getMessage())
                                .build()
                );
    }
}
