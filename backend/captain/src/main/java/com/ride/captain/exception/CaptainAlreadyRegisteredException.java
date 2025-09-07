package com.ride.captain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CaptainAlreadyRegisteredException extends RuntimeException {

    public CaptainAlreadyRegisteredException(String msg){
        super(msg);
    }

}
