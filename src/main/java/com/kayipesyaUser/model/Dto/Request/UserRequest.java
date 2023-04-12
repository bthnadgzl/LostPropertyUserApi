package com.kayipesyaUser.model.Dto.Request;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@Data
public class UserRequest {
    private String email;
    private String password;
}
