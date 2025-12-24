package com.bugtracker.controller;

import com.bugtracker.model.Role;
import com.bugtracker.model.User;
import com.bugtracker.repository.UserRepository;
import com.bugtracker.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/register")
    public String register(@RequestBody User user){

        // if username exists in the database
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return "Username already exists!!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // default role
        if(user.getRole() == null){
            user.setRole(Role.USER);
        }

        userRepository.save(user);

        return "User Registered Successfully !!";
    }


    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Invalid username or password !!");
            return error;
        }

        User loggedInUser = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        String token = jwtUtil.generateToken(
                loggedInUser.getUsername(),
                loggedInUser.getRole().name()
        );

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", loggedInUser.getRole().name());

        return response;
    }
}
