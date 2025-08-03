package com.workintech.spring17challenge.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class ApiException extends RuntimeException{
    HttpStatus HttpStatus;

    public ApiException(String message, HttpStatus HttpStatus) {
        super(message);
        this.HttpStatus = HttpStatus;
    }
}
