package com.airline.reserve.common;

import com.airline.reserve.domain.Flight;
import com.airline.reserve.domain.Reservation;

public class Client {
    private String name;
    private int age;
    private String gender;

    public Client(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
