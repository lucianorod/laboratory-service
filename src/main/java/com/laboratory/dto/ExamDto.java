package com.laboratory.dto;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ExamDto {

    @NotNull
    private String name;

    private String examType;

    private Set<Integer> laboratories;
}
