package com.airline.reserve.domain;

import com.airline.reserve.common.Client;
import com.airline.reserve.common.Money;
import lombok.ToString;

import java.util.List;

@ToString
public class Reservation {
    private Flight flight;
    private List<Client> clientList;
    private Money money;
    private int peopleCount;

    public Reservation(Flight flight, List<Client> clientList, Money money, int peopleCount) {
        this.flight = flight;
        this.clientList = clientList;
        this.money = money;
        this.peopleCount = peopleCount;
    }
    
}
