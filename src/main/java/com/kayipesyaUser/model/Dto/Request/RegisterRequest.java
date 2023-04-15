package com.kayipesyaUser.model.Dto.Request;

import com.kayipesyaUser.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
}
