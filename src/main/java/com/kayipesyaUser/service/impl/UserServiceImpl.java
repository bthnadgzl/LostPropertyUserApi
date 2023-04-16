package com.kayipesyaUser.service.impl;

import com.kayipesyaUser.constant.UserRole;
import com.kayipesyaUser.exception.CustomException;
import com.kayipesyaUser.model.Dto.Request.LoginRequest;
import com.kayipesyaUser.model.Dto.Request.RegisterRequest;
import com.kayipesyaUser.model.Dto.Response.UserResponse;
import com.kayipesyaUser.model.User;
import com.kayipesyaUser.repository.UserRepository;
import com.kayipesyaUser.security.TokenManager;
import com.kayipesyaUser.service.EmailService;
import com.kayipesyaUser.service.UserService;
import com.kayipesyaUser.util.Mapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static com.kayipesyaUser.util.UniversityMailValidation.universityMailValidation;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManagerBean;
    private TokenManager tokenManager;
    private Mapper mapper;

    private EmailService emailService;

    @Override
    public ResponseEntity<String> login(LoginRequest loginRequest) {
        try {
            authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            String token = tokenManager.generateToken(loginRequest.getEmail(), userRepository.findByEmail(loginRequest.getEmail()).get().getUserRole());
            return ResponseEntity.ok(token);
        }
        catch (DisabledException e){
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE,"Confirm your email.");
        }
        catch (Exception e){
           throw new CustomException(HttpStatus.BAD_REQUEST,"Invalid username or password.");
        }


    }

    @Override
    public ResponseEntity<String> register(RegisterRequest registerRequest,String siteUrl) {

        universityMailValidation(registerRequest.getEmail()); // Checks if the university email or not.
        if(!userRepository.existsByEmail(registerRequest.getEmail())){
            User user = mapper.registerRequestToUserEntity(registerRequest);
            user.setUserRole(UserRole.ROLE_CLIENT);
            user.setEnabled(false);
            String randomCode = RandomString.make(64);
            user.setVerificationCode(randomCode);
            userRepository.save(user);
            emailService.sendVerificationMail(user,siteUrl);
            return ResponseEntity.ok("Email confirm needed.");
        }
        else{
            User user = userRepository.findByEmail(registerRequest.getEmail()).get();
            if(!user.isEnabled()){
                String randomCode = RandomString.make(64);
                user.setVerificationCode(randomCode);
                userRepository.save(user);
                emailService.sendVerificationMail(user,siteUrl);
                return ResponseEntity.ok("Verification email resended.");
            }
            throw new CustomException(HttpStatus.BAD_REQUEST,"EMAIL ALREADY IN USE! (Register with your university mail).");
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
    @Override
    public boolean verify(String verificationCode){
        Optional<User> user = userRepository.findByVerificationCode(verificationCode);
        if(!user.isPresent() || user.get().isEnabled()){
            return false;
        }
        else{
            user.get().setEnabled(true);
            user.get().setVerificationCode(null);
            userRepository.save(user.get());
            return true;
        }

    }

}
