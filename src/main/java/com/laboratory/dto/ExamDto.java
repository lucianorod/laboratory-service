package com.laboratory.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExamDto {

    @ApiModelProperty(hidden = true)
    private Long id;

    private String name;

    private String examType;

}
