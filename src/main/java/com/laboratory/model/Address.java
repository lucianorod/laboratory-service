package com.laboratory.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(name = "street")
    private String street;

    @NotEmpty
    @Column(name = "neighborhood")
    private String neighborhood;

    @NotEmpty
    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "complement")
    private String complement;

    @NotEmpty
    @Column(name = "postal_code")
    private String postalCode;
}
