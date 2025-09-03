package com.uber.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyRegisteredException extends RuntimeException{


    public UserAlreadyRegisteredException(String msg){
        super(msg);
    }
}
