package com.jspm.SmartErp.controller;

import com.jspm.SmartErp.dto.LoginRequest;
import com.jspm.SmartErp.dto.JwtResponse;
import com.jspm.SmartErp.model.*;
import com.jspm.SmartErp.security.JwtUtils;
import com.jspm.SmartErp.security.UserDetailsImpl;
import com.jspm.SmartErp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication.getName());

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername());

        String name = "";
        if (user instanceof Student) {
            name = ((Student) user).getName();
        } else if (user instanceof Faculty) {
            name = ((Faculty) user).getFullName();
        } else if (user instanceof AdminProfile) {
            name = ((AdminProfile) user).getFullName();
        }

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserId(),
                userDetails.getUsername(), user.getRole(), name));
    }
}