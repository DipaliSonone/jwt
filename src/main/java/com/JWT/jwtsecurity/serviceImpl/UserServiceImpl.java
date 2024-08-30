package com.JWT.jwtsecurity.serviceImpl;

import com.JWT.jwtsecurity.repo.UserRepo;
import com.JWT.jwtsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetailsService userDetailService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username){
                return (UserDetails) userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
            }
        };
    }
}
