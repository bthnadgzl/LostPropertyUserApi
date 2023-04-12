package com.kayipesyaUser.service;

import com.kayipesyaUser.model.Dto.Request.LoginRequest;
import com.kayipesyaUser.model.Dto.Request.UserRequest;
import com.kayipesyaUser.model.Dto.Response.LoginResponse;
import com.kayipesyaUser.model.Dto.Response.UserResponse;
import com.kayipesyaUser.model.User;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {
    ResponseEntity<UserResponse> create(UserRequest userRequest);
    ResponseEntity<UserResponse> update(UserRequest userRequest);
    ResponseEntity<UserResponse> delete(UUID userUuid);
    ResponseEntity<LoginResponse> login(LoginRequest loginRequest);
}
