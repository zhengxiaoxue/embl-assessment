package com.embl.assessment;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonControllerDeleteTest extends BaseTest {
    @Test
    public void test_unauthorized() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix + "/people/3");
        ResponseEntity<String> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, null, String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


    @Test
    public void test_delete() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix + "/people/3");
        HttpEntity request = new HttpEntity(null, headers);
        ResponseEntity resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, request, String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
