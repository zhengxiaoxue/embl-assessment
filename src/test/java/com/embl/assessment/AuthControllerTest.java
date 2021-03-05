package com.embl.assessment;

import com.embl.assessment.dto.request.AuthRequest;
import com.embl.assessment.dto.response.AuthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthControllerTest extends BaseTest{

    @Test
    public void test_bad_request_null_body(){
        ResponseEntity<AuthResponse> resp = restTemplate.exchange(urlPrefix+"/auth", HttpMethod.POST, null, AuthResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_bad_request_miss_field(){
        AuthRequest authRequest = AuthRequest.builder().username("admin").build();
        HttpEntity request = new HttpEntity(authRequest, null);
        ResponseEntity<AuthResponse> resp = restTemplate.exchange(urlPrefix+"/auth", HttpMethod.POST, request, AuthResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_auth_wrong_identity(){
        AuthRequest wrongPassRequest = AuthRequest.builder().username("admin").password("wrong_pass").build();
        HttpEntity request = new HttpEntity(wrongPassRequest, null);
        ResponseEntity<AuthResponse> resp = restTemplate.exchange(urlPrefix+"/auth", HttpMethod.POST, request, AuthResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

        AuthRequest wrongUserNameRequest = AuthRequest.builder().username("wrong_user").password("admin").build();
        HttpEntity request2 = new HttpEntity(wrongUserNameRequest, null);
        ResponseEntity<AuthResponse> resp2 = restTemplate.exchange(urlPrefix+"/auth", HttpMethod.POST, request2, AuthResponse.class);
        assertThat(resp2.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void test_auth(){
        AuthRequest authRequest = AuthRequest.builder().username("admin").password("admin").build();
        HttpEntity request = new HttpEntity(authRequest, null);
        ResponseEntity<AuthResponse> resp = restTemplate.exchange(urlPrefix+"/auth", HttpMethod.POST, request, AuthResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}
