package com.JWT.jwtsecurity.serviceImpl;

import com.JWT.jwtsecurity.dto.JWTAuthenticationResponse;
import com.JWT.jwtsecurity.dto.SignInRequest;
import com.JWT.jwtsecurity.dto.SignUpRequest;
import com.JWT.jwtsecurity.entity.Role;
import com.JWT.jwtsecurity.entity.User;
import com.JWT.jwtsecurity.repo.UserRepo;
import com.JWT.jwtsecurity.service.AuthenticationService;
import com.JWT.jwtsecurity.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    public User signUp(SignUpRequest signUpRequest){
        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmail(signUpRequest.getEmail());
        user.setPhoneNo(signUpRequest.getPhoneNo());
        /*user.setRole(Role.USER);*/
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepo.save(user);
    }

    public JWTAuthenticationResponse signIn(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        var user = userRepo.findByEmail(signInRequest.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("Invalid Email or password"));

        var jwt = jwtService.generateToken((UserDetails) user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), (UserDetails) user);

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;

    }
}
