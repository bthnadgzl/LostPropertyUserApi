package com.kayipesyaUser.controller;

import com.kayipesyaUser.exception.CustomException;
import com.kayipesyaUser.model.Dto.Request.LoginRequest;
import com.kayipesyaUser.model.Dto.Request.RegisterRequest;
import com.kayipesyaUser.model.Dto.Response.UserResponse;
import com.kayipesyaUser.security.TokenManager;
import com.kayipesyaUser.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
       return userService.login(loginRequest);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest,HttpServletRequest request){
        return userService.register(registerRequest,getSiteUrl(request));
    }
    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String email){return userService.delete(email);
    }
    @GetMapping("/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> search(@PathVariable String email){
        return userService.search(email);
    }
    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<UserResponse> whoIsUser(HttpServletRequest request){
        return userService.whoIsUser(request);
    }

    @GetMapping("/verify") // Link includes the verificationCode this method gets the code and sends to verify(code) method.
    public ResponseEntity<String> verifyUser(@Param("code") String code){
        if(userService.verify(code)){
            return ResponseEntity.ok("Verification complete.");
        }
        else{
            return ResponseEntity.ok("Verification failed.");
        }
    }
    public String getSiteUrl(HttpServletRequest request){
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(),"");
    }


}
