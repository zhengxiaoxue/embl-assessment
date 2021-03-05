package com.embl.assessment.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Used to request authentication token
 */
@Data
@Builder
public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
