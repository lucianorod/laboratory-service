package com.laboratory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@ToString
@NoArgsConstructor
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

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @NotNull
    private Address address;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "laboratory_exam", joinColumns = {@JoinColumn(name = "laboratory_id")},
            inverseJoinColumns = {@JoinColumn(name = "exam_id")})
    private Collection<Exam> exams;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "is_removed")
    private boolean removed;
}