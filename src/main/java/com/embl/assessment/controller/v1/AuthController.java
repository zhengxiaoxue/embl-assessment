package com.embl.assessment.controller.v1;

import com.embl.assessment.dto.request.AuthRequest;
import com.embl.assessment.dto.response.AuthResponse;
import com.embl.assessment.security.UserDetailImpl;
import com.embl.assessment.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/auth")
public class AuthController{
    AuthenticationManager authenticationManager;

    JwtUtils jwtUtils;

    PasswordEncoder encoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;
    }

    //curl -H "Content-Type: application/json" -X POST -d '{"username": "admin", "password":"admin"}' "http://127.0.0.1:8080/v1/api/auth"
    @PostMapping("")
    public AuthResponse authUser(@Validated @RequestBody AuthRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        UserDetailImpl userDetailImpl = (UserDetailImpl) authentication.getPrincipal();
        List<String> roles = userDetailImpl.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new AuthResponse(token, loginRequest.getPassword(), roles);
    }


}
