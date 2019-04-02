package com.laboratory.model;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
