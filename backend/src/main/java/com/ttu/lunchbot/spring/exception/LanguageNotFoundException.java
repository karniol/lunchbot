package com.ttu.lunchbot.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LanguageNotFoundException extends Exception {

    public LanguageNotFoundException(String msg) {
        super(msg);
    }

}
