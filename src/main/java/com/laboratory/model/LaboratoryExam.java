package com.laboratory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "laboratory_exam", uniqueConstraints = {@UniqueConstraint(columnNames = {"laboratory_id", "exam_id"})})
public class LaboratoryExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "laboratory_id")
    private Laboratory laboratory;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "is_removed")
    private boolean removed;
}
