package com.example.AuthenticationServer.AOP;

import com.example.AuthenticationServer.exception.TokenEmailNotMatchedException;
import com.example.AuthenticationServer.exception.RepeatedFieldException;
import com.example.AuthenticationServer.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleException(Exception e){
        return new ResponseEntity(ErrorResponse.builder().message("Error").build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {TokenEmailNotMatchedException.class})
    public ResponseEntity<ErrorResponse> handleTokenEmailNotMatchedException(TokenEmailNotMatchedException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {RepeatedFieldException.class})
    public ResponseEntity<ErrorResponse> handleRepeatedFieldException(RepeatedFieldException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e){
        return new ResponseEntity(ErrorResponse.builder().message(e.getMessage()).build(), HttpStatus.OK);
    }

}
