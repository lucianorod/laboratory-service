package com.laboratory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "complement")
    private String complement;

    @Column(name = "postal_code")
    private String postalCode;
}
