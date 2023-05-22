package com.kayipesyaUser.service;

import com.kayipesyaUser.model.Dto.Request.LoginRequest;
import com.kayipesyaUser.model.Dto.Request.RegisterRequest;
import com.kayipesyaUser.model.Dto.Response.LoginResponse;
import com.kayipesyaUser.model.Dto.Response.UserResponse;
import com.kayipesyaUser.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);
    ResponseEntity<String> register(RegisterRequest registerRequest,String siteUrl);
    ResponseEntity<String> delete(String email);
    ResponseEntity<UserResponse> search(String email);
    ResponseEntity<UserResponse> whoIsUser(HttpServletRequest request);
    boolean verify(String verificationCode);


}
