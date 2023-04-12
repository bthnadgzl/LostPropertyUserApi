package com.kayipesyaUser.util;

import com.kayipesyaUser.constant.AvailableUniversities;
import com.kayipesyaUser.model.Dto.Request.UserRequest;
import com.kayipesyaUser.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
@AllArgsConstructor
public class Mapper {
    private final ModelMapper modelMapper;

    public User userRequestToUserEntity(UserRequest userRequest){
        String universityMailCode = AvailableUniversities.ITU.getUniversityMailCode();
        User user = modelMapper.map(userRequest,User.class);
        user.setUsername(userRequest.getEmail().replaceAll(universityMailCode,""));
        return user;
    }
    public <A,B> A mapSourceToTarget(B source,Class<A> targetClass){
        return modelMapper.map(source,targetClass);
    }

}
