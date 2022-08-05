package com.saxakiil.beautyspacebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contacts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phoneNumber")
        })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private long id;
    private String phoneNumber;
    private String telegram;
    private String address;
}
