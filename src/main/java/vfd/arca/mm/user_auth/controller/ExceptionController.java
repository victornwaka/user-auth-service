package vfd.arca.mm.user_auth.controller;

import vfd.arca.mm.user_auth.constants.Constant;
import org.springframework.http.HttpStatus;
import vfd.arca.mm.user_auth.exceptions.UserAuthenticationException;
import vfd.arca.mm.user_auth.response.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import vfd.arca.mm.user_auth.exceptions.UserNotFoundException;

import java.net.URISyntaxException;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<Error> uriSyntaxExceptionHandler(URISyntaxException e){
        return new ResponseEntity<>(new Error(Constant.HTTP_CLIENT_ERROR, e.getMessage()),
                HttpStatus.BAD_GATEWAY);
    }
    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<Error> UserLoginExceptionHandler(UserAuthenticationException e){
        return new ResponseEntity<>(new Error(Constant.INVALID_CRED, e.getMessage()),
                HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Error> httpErrorHandler(HttpClientErrorException e){
        return new ResponseEntity<>(new Error(String.valueOf(e.getStatusCode().value()),
                e.getResponseBodyAsString()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> UserNotFoundExceptionHandler(UserNotFoundException d){
        return new ResponseEntity<>(new Error(Constant.USER_NOT_FOUND, d.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> globalErrorHandler(Exception e){
        return new ResponseEntity<>(new Error(Constant.INTERNAL_SERVER_ERROR, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
