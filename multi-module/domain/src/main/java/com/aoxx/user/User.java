package com.aoxx.user;

import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "USER_INFO")
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    protected User() {}

    public User(String name) {
        this.name = name;
    }

}
