package com.embl.assessment.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
    private String token;
    private String userName;
    private List<String> roles;

    public AuthResponse(String token, String userName, List<String> roles){
        this.token = token;
        this.userName = userName;
        this.roles = roles;
    }
}
