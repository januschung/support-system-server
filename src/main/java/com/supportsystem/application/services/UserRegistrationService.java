package com.supportsystem.application.services;

import com.supportsystem.application.domains.AppUser;

public interface UserRegistrationService {

    AppUser registerUser(String username, String password, String email);
}
