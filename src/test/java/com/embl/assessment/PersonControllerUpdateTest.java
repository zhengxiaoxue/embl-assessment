package com.embl.assessment;

import com.embl.assessment.dto.request.PersonRequest;
import com.embl.assessment.dto.response.PersonResponse;
import com.embl.assessment.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonControllerUpdateTest extends BaseTest{

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
        ResponseEntity<PersonResponse> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, request, PersonResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_bad_request_with_miss_filed_id(){
        ArrayList<PersonRequest.PersonDTO> people = new ArrayList<>();
        //no firstname field
        people.add(PersonRequest.PersonDTO.builder().firstName("xiaoxue").lastName("zheng").favouriteColor("blue").age(18).build());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people");
        HttpEntity request = new HttpEntity(new PersonRequest(people), headers);
        ResponseEntity<Object> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, request, Object.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test_update(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlPrefix+"/people");
        ArrayList<PersonRequest.PersonDTO> people = new ArrayList<>();
        Long id = 2L;String firstName = "xiaoxue", lastName = "zheng", favouriteColor = "blue"; int age = 18;
        people.add(PersonRequest.PersonDTO.builder().firstName(firstName).lastName(lastName).favouriteColor(favouriteColor).id(id).age(age).build());
        PersonRequest personRequest = new PersonRequest(people);
        HttpEntity request = new HttpEntity(personRequest, headers);
        ResponseEntity<PersonResponse> resp = this.restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, request, PersonResponse.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);

        Optional<Person> personOptional = personRepository.findById(id);
        assertThat(personOptional.isPresent()).isTrue();
        assertThat(personOptional.get().getAge()).isEqualTo(age);
        assertThat(personOptional.get().getFirstName()).isEqualTo(firstName);
        assertThat(personOptional.get().getLastName()).isEqualTo(lastName);
        assertThat(personOptional.get().getFavouriteColor()).isEqualTo(favouriteColor);
    }

}
