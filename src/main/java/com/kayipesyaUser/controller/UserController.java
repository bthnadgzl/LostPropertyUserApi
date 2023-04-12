package com.kayipesyaUser.controller;

import com.kayipesyaUser.model.Dto.Request.LoginRequest;
import com.kayipesyaUser.model.Dto.Request.UserRequest;
import com.kayipesyaUser.model.Dto.Response.LoginResponse;
import com.kayipesyaUser.model.Dto.Response.UserResponse;
import com.kayipesyaUser.model.User;
import com.kayipesyaUser.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        return userService.create(userRequest);
    }
    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest){
        return userService.update(userRequest);

    }
    @DeleteMapping
    @RequestMapping("/{uuid}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable UUID uuid){
        return userService.delete(uuid);
    }
    @GetMapping
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }



}
