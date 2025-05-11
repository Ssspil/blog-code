package com.aoxx.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;


    private String name;

    protected User() {}

    public User(String name) {
        this.name = name;
    }

}
