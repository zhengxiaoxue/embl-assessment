package com.embl.assessment;

import com.embl.assessment.dto.request.PersonRequest;
import com.embl.assessment.dto.response.PersonResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonControllerCreateTest extends BaseTest{

    @Test
    public void test_unauthorized(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people");
        HttpEntity request = new HttpEntity(null, null);
        ResponseEntity<PersonResponse> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.POST, request, PersonResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void test_bad_request_null_body(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people");
        HttpEntity request = new HttpEntity(null, headers);
        ResponseEntity<PersonResponse> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.POST, request, PersonResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_bad_request_with_miss_filed(){
        ArrayList<PersonRequest.PersonDTO> people = new ArrayList<>();
        //no firstname field
        people.add(PersonRequest.PersonDTO.builder().lastName("zheng").favouriteColor("blue").age(18).build());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people");
        HttpEntity request = new HttpEntity(new PersonRequest(people), headers);
        ResponseEntity<Object> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.POST, request, Object.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_create(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people");
        HttpEntity request = new HttpEntity(buildPersonRequest(), headers);
        ResponseEntity<PersonResponse> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.POST, request, PersonResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private PersonRequest buildPersonRequest(){
        ArrayList<PersonRequest.PersonDTO> people = new ArrayList<>();
        people.add(PersonRequest.PersonDTO.builder().firstName("xiaoxue").lastName("zheng").favouriteColor("blue").age(18).build());
        people.add(PersonRequest.PersonDTO.builder().firstName("he").lastName("shan").favouriteColor("black").age(80).build());
        PersonRequest personRequest = new PersonRequest(people);
        return personRequest;
    }
}
