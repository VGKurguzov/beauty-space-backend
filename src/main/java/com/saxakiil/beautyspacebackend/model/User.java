package com.saxakiil.beautyspacebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    private String email;
    private String username;

    @JsonIgnore
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_profile_id")
    private AccountProfile accountProfile;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "is_activated")
    @Builder.Default
   private boolean isActivated = Boolean.FALSE;
}
