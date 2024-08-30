package com.JWT.jwtsecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{
    UserDetailsService userDetailService();
}
