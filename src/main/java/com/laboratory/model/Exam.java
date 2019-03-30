package com.laboratory.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_type_id")
    private ExamType examType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "exams")
    private Collection<Laboratory> laboratories;

    @Column(name = "is_removed")
    private boolean removed;
}
