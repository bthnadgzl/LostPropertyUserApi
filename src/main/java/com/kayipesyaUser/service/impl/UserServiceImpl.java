package com.kayipesyaUser.service.impl;

import com.kayipesyaUser.model.Dto.Request.LoginRequest;
import com.kayipesyaUser.model.Dto.Request.RegisterRequest;
import com.kayipesyaUser.model.Dto.Response.UserResponse;
import com.kayipesyaUser.model.User;
import com.kayipesyaUser.repository.UserRepository;
import com.kayipesyaUser.security.TokenManager;
import com.kayipesyaUser.service.UserService;
import com.kayipesyaUser.util.Mapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManagerBean;
    private TokenManager tokenManager;
    private Mapper mapper;
    @Override
    public ResponseEntity<String> login(LoginRequest loginRequest) {
        //try {
            authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            String token = tokenManager.generateToken(loginRequest.getEmail(), userRepository.findByEmail(loginRequest.getEmail()).get().getUserRoleList());
            return ResponseEntity.ok(token);
        //}
        //catch (AuthenticationException e){
        //    System.out.println(e.getMessage());
        //}

    }

    @Override
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        if(!userRepository.existsByEmail(registerRequest.getEmail())){
            User user = mapper.registerRequestToUserEntity(registerRequest);
            userRepository.save(user);
            String token = tokenManager.generateToken(user.getEmail(),user.getUserRoleList());
            return ResponseEntity.ok(token);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<String> delete(String email) {
        userRepository.deleteByEmail(email);
        return ResponseEntity.ok(email);
    }

    @Override
    public ResponseEntity<UserResponse> search(String email) {
        UserResponse userResponse = mapper.mapSourceToTarget(userRepository.findByEmail(email),UserResponse.class);
        return ResponseEntity.ok(userResponse);
    }

    @Override
    public ResponseEntity<UserResponse> whoIsUser(HttpServletRequest request) {
        User user = userRepository.findByEmail(tokenManager.getEmailFromToken(tokenManager.resolveToken(request))).get();
        UserResponse userResponse = mapper.mapSourceToTarget(user,UserResponse.class);
        return ResponseEntity.ok(userResponse);
    }


}
