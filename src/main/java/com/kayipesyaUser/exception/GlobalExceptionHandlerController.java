package com.kayipesyaUser.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



import java.io.IOException;
import java.nio.file.AccessDeniedException;
@ControllerAdvice
public class GlobalExceptionHandlerController{

    @ExceptionHandler(CustomException.class)
    public void handleCustomException(HttpServletResponse response,CustomException e) throws IOException {
        response.sendError(e.getHttpStatus().value(),e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(),"Something went wrong.");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(),"Access denied.");
    }
}
