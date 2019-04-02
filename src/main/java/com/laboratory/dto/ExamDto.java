package com.laboratory.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ExamDto {

    private Long id;

    private String name;

    private String examType;

    private Set<LaboratoryDto> laboratories;
}
