package com.JWT.jwtsecurity.service;

import com.JWT.jwtsecurity.dto.JWTAuthenticationResponse;
import com.JWT.jwtsecurity.dto.SignInRequest;
import com.JWT.jwtsecurity.dto.SignUpRequest;
import com.JWT.jwtsecurity.entity.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JWTAuthenticationResponse signIn(SignInRequest signInRequest);
}
