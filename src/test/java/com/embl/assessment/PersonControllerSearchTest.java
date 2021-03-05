package com.embl.assessment;

import com.embl.assessment.dto.request.PersonRequest;
import com.embl.assessment.dto.response.PersonResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonControllerSearchTest extends BaseTest{
    @Test
    public void test_unauthorized(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people");
        HttpEntity request = new HttpEntity(null);
        ResponseEntity<PersonResponse> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, PersonResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void test_bad_request_miss_field(){
        String firstName = "John";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people")
                .queryParam("firstname", firstName);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<PersonResponse> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, PersonResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String lastName = "John";
        UriComponentsBuilder builder2 = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people")
                .queryParam("firstname", lastName);
        HttpEntity request2 = new HttpEntity(headers);
        ResponseEntity<PersonResponse> resp2 = this.restTemplate.exchange(builder2.toUriString(), HttpMethod.GET, request2, PersonResponse.class);
        assertThat(resp2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_search_no_return(){
        String firstName = "NotExist";
        String lastName = "NotExist";
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstName);
        params.put("lastname", lastName);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people")
                .queryParam("firstname", firstName)
                .queryParam("lastname", lastName);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<PersonResponse> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, PersonResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isNotNull();
        assertThat(resp.getBody().getPerson()).isNullOrEmpty();
    }

    @Test
    public void test_search_should_return(){
        String firstName = "John";
        String lastName = "Keynes";
        Map<String, String> params = new HashMap<>();
        params.put("firstname", firstName);
        params.put("lastname", lastName);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people")
                .queryParam("firstname", firstName)
                .queryParam("lastname", lastName);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<PersonResponse> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request, PersonResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resp.getBody()).isNotNull();
        PersonRequest.PersonDTO personDTO = PersonRequest.PersonDTO.builder().firstName(firstName).lastName(lastName).age(29).favouriteColor("red").id(1L).build();
        assertThat(resp.getBody().getPerson()).containsOnlyOnce(personDTO);
    }

}
