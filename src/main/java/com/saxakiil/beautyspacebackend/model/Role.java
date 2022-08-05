package com.saxakiil.beautyspacebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saxakiil.beautyspacebackend.util.EnumRole;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private long id;

    @Enumerated(EnumType.STRING)
    private EnumRole name;

}