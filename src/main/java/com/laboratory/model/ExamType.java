package com.laboratory.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exam_type")
public class ExamType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;
}
