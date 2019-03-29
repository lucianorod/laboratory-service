package com.laboratory.dto;

import com.laboratory.model.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LaboratoryDto {

    private String name;

    private Address address;
}
