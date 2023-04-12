package com.kayipesyaUser.service.impl;

import com.kayipesyaUser.model.Dto.Request.LoginRequest;
import com.kayipesyaUser.model.Dto.Request.UserRequest;
import com.kayipesyaUser.model.Dto.Response.LoginResponse;
import com.kayipesyaUser.model.Dto.Response.UserResponse;
import com.kayipesyaUser.model.User;
import com.kayipesyaUser.repository.UserRepository;
import com.kayipesyaUser.service.UserService;
import com.kayipesyaUser.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private Mapper mapper;
    @Override
    public ResponseEntity<UserResponse> create(UserRequest userRequest) {
        User user = mapper.userRequestToUserEntity(userRequest);
        UserResponse userResponse = mapper.mapSourceToTarget(user,UserResponse.class);
        userRepository.save(user);
        return ResponseEntity.ok(userResponse);
    }
    @Override
    public ResponseEntity<UserResponse> update(UserRequest userRequest){
        Optional<User> userOpt = Optional.ofNullable(userRepository.findByEmail(userRequest.getEmail()));
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(userRequest.getPassword());
            userRepository.save(user);
            UserResponse userResponse = mapper.mapSourceToTarget(user, UserResponse.class);
            return ResponseEntity.ok(userResponse);
        }
        else
            return ResponseEntity.notFound().build();
    }
    @Override
    public ResponseEntity<UserResponse> delete(UUID uuid) {
        userRepository.deleteById(uuid);
        Optional<User> user = userRepository.findById(uuid);
        if(user.isPresent()){
            UserResponse userResponse = mapper.mapSourceToTarget(user.get(), UserResponse.class);
            return ResponseEntity.ok(userResponse);
        }
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        return null;
    }

}
