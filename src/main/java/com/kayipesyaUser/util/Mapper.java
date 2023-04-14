package com.kayipesyaUser.util;

import com.kayipesyaUser.constant.AvailableUniversities;
import com.kayipesyaUser.model.Dto.Request.LoginRequest;
import com.kayipesyaUser.model.Dto.Request.RegisterRequest;
import com.kayipesyaUser.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
@AllArgsConstructor
public class Mapper {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public User registerRequestToUserEntity(RegisterRequest registerRequest){
        String universityMailCode = AvailableUniversities.ITU.getUniversityMailCode();
        User user = modelMapper.map(registerRequest,User.class);
        user.setUsername(registerRequest.getEmail().replaceAll(universityMailCode,""));
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        return user;
    }
    public <A,B> A mapSourceToTarget(B source,Class<A> targetClass){
        return modelMapper.map(source,targetClass);
    }

}
