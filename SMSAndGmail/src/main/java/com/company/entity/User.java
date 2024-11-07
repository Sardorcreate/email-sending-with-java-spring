package com.company.entity;

import com.company.enums.Role;
import com.company.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "usss")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
    @Column
    private String login;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private Boolean visible;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Mail> mailList;
}
