package com.embl.assessment.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Builder(access = AccessLevel.PUBLIC)

@Table(name = "PERSON", uniqueConstraints = {@UniqueConstraint(columnNames = "first_name"),
        @UniqueConstraint(columnNames = "last_name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "favourite_color")
    private String favouriteColor;
}
