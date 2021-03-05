package com.embl.assessment.controller.v1;


import com.embl.assessment.dto.request.checks.PersonCreateChecks;
import com.embl.assessment.dto.request.PersonRequest;
import com.embl.assessment.dto.request.checks.PersonUpdateChecks;
import com.embl.assessment.dto.response.PersonResponse;
import com.embl.assessment.entity.Person;
import com.embl.assessment.repository.PersonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Example;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/people")
public class PersonController {

    private final PersonRepository personRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    //curl -H "Content-Type: application/json" -H "Authorization: Bearer " "http://127.0.0.1:8080/v1/api/people?firstname=John&lastname=Keynes"
    @GetMapping
    public PersonResponse search(@RequestParam("firstname")String firstName, @RequestParam("lastname")String lastName){
        Person person = Person.builder().firstName(firstName).lastName(lastName).build();
        return new PersonResponse(objectMapper.convertValue(personRepository.findAll(Example.of(person)), new TypeReference<List<PersonRequest.PersonDTO>>(){}));
    }

    /**
     curl -H "Content-Type: application/json" -H "Authorization: Bearer change_me" -X POST -d '{
            "person": [
    {
        "firstName": "a",
            "lastName": "b",
            "age": 1,
            "favouriteColor": "red"
    },
    {
        "firstName": "c",
            "lastName": "d",
            "age": 1,
            "favouriteColor": "blue"
    },
    {
        "firstName": "e",
            "lastName": "f",
            "age": 1,
            "favouriteColor": "gree"
    }
    ]
}' "http://127.0.0.1:8080/v1/api/people"
     */
    @PostMapping
    public PersonResponse create(@RequestBody @Validated(PersonCreateChecks.class) PersonRequest request){
        List<Person> people = personRepository.saveAll(objectMapper.convertValue(request.getPerson(), new TypeReference<List<Person>>() {}));
        return new PersonResponse(objectMapper.convertValue(people, new TypeReference<List<PersonRequest.PersonDTO>>(){}));
    }

    /**
     * curl -H "Content-Type: application/json" -H "Authorization: Bearer change_me" -X PUT -d '{
     *             "person": [
     *     {
     *         "id": 3,
     *         "firstName": "aa",
     *             "lastName": "bb",
     *             "age": 2,
     *             "favouriteColor": "green"
     *     },
     *     {
     *         "id": 4,
     *         "firstName": "cc",
     *             "lastName": "dd",
     *             "age": 2,
     *             "favouriteColor": "green"
     *     }
     *     ]
     * }' "http://127.0.0.1:8080/v1/api/people"
     * @param request
     * @return
     */
    @PutMapping
    public PersonResponse update(@RequestBody @Validated(PersonUpdateChecks.class) PersonRequest request){
        List<Person> people = personRepository.saveAll(objectMapper.convertValue(request.getPerson(), new TypeReference<List<Person>>() {}));
        return new PersonResponse(objectMapper.convertValue(people, new TypeReference<List<PersonRequest.PersonDTO>>(){}));
    }

    /**
     * This API doesn't support batch operation
     *
     * curl -H "Content-Type: application/json" -H "Authorization: Bearer change_me" -X DELETE "http://127.0.0.1:8080/v1/api/people/3"
     * @param id The id of person entity expected to delete
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteByIds(@PathVariable("id") Long id){
        personRepository.deleteById(id);
        return String.format("delete %s success", id);
    }


}
