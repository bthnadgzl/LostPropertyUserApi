package com.kayipesyaUser.service;

import com.kayipesyaUser.model.User;

public interface EmailService {
    void sendVerificationMail(User user, String siteUrl);
}
