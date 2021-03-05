package com.embl.assessment.dto.request;

import com.embl.assessment.dto.request.checks.PersonCreateChecks;
import com.embl.assessment.dto.request.checks.PersonUpdateChecks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {
    @NotEmpty(groups = {PersonCreateChecks.class, PersonUpdateChecks.class})
    @Valid
    List<PersonDTO> person;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonDTO {
        @NotNull(groups = PersonUpdateChecks.class)
        private Long id;
        @NotEmpty(groups = {PersonCreateChecks.class, PersonUpdateChecks.class})
        private String firstName;
        @NotEmpty(groups = {PersonCreateChecks.class, PersonUpdateChecks.class})
        private String lastName;
        @NotNull(groups = {PersonCreateChecks.class, PersonUpdateChecks.class})
        private Integer age;
        @NotEmpty(groups = {PersonCreateChecks.class, PersonUpdateChecks.class})
        private String favouriteColor;
    }
}
