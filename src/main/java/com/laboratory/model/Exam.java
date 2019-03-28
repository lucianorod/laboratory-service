package com.laboratory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_type_id")
    private ExamType examType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "exams")
    private Collection<Laboratory> laboratories;
}
