package com.kayipesyaUser.service.impl;

import com.kayipesyaUser.exception.CustomException;
import com.kayipesyaUser.model.User;
import com.kayipesyaUser.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
@AllArgsConstructor
@Component
public class EmailServiceImpl implements EmailService {
    private JavaMailSender mailSender;
    @Override
    public void sendVerificationMail(User user, String siteUrl) {
        String toAddress = user.getEmail();
        String fromAddress = "lostpropertyapail.com";
        String senderName = "Lostproperty";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
        }
        catch(MessagingException e){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Message error.");
        } catch (UnsupportedEncodingException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Unsupported encoding.");
        }

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteUrl + "/user/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        try {
            helper.setText(content, true);
        } catch (MessagingException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Message error");
        }

        mailSender.send(message);
    }
}
