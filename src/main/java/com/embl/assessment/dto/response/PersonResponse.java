package com.embl.assessment.dto.response;

import com.embl.assessment.dto.request.PersonRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The created or updated person
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    List<PersonRequest.PersonDTO> person;
}
