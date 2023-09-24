package com.iky.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;
    private String name;
    private int foundingYear;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Person_FK")
    private Person CEO;
}
