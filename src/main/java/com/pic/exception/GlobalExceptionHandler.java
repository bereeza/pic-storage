package com.pic.exception;

import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse er = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialException(BadCredentialsException e) {
        ErrorResponse er = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(er, HttpStatus.CONFLICT);
    }
}
