package com.laboratory.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exam_type")
public class ExamType {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;
}
