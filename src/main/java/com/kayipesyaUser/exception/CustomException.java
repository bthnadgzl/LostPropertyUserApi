package com.kayipesyaUser.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }
    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
