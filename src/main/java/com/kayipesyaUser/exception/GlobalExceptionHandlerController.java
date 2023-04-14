package com.kayipesyaUser.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Map;

public class GlobalExceptionHandlerController{

    @ExceptionHandler(CustomException.class)
    public void handleCustomException(HttpServletResponse request,CustomException e) throws IOException {
        request.sendError(e.getHttpStatus().value(),e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse request) throws IOException {
        request.sendError(HttpStatus.BAD_REQUEST.value(),"Something went wrong.");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse request) throws IOException {
        request.sendError(HttpStatus.FORBIDDEN.value(),"Access forbidden.");
    }
}
