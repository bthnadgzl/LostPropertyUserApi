package com.kayipesyaUser.service;

import com.kayipesyaUser.model.Dto.Request.LoginRequest;
import com.kayipesyaUser.model.Dto.Request.RegisterRequest;
import com.kayipesyaUser.model.Dto.Response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> login(LoginRequest loginRequest);
    ResponseEntity<String> register(RegisterRequest registerRequest);
    ResponseEntity<String> delete(String email);
    ResponseEntity<UserResponse> search(String email);
    ResponseEntity<UserResponse> whoIsUser(HttpServletRequest request);

}
