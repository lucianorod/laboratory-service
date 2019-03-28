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
@Table(name = "laboratory")
public class Laboratory {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "active")
    private boolean active;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "laboratory_exam", joinColumns = {@JoinColumn(name = "laboratory_id")},
            inverseJoinColumns = {@JoinColumn(name = "exam_id")})
    private Collection<Exam> exams;
}
