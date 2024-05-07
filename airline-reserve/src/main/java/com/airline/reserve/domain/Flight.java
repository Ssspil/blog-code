package com.airline.reserve.domain;

import com.airline.reserve.common.Client;
import com.airline.reserve.common.Money;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Created by rius0918@gmail.com on 2024. 5. 15.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */

// 항공편 클래스
public class Flight {
    // 항공편 번호
    private String flightNumber;
    // 항공사
    private String airline;
    // 항공편 비용
    private Money fee;
    // 출발 일시
    private LocalDateTime departureDateTime;
    // 도착 일시
    private LocalDateTime arrivalDateTime;
    // 노선 정보
    private Route route;

    @Builder
    public Flight(String flightNumber, String airline, Money fee, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, Route route) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.fee = fee;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.route = route;
    }

    // 항공권 예매
    public Reservation reserve(Client ... clients){
        List<Client> clientList = List.of(clients);
        int peopleCount = clientList.size();
        return new Reservation(this, clientList, calculateFee(peopleCount), peopleCount);
    }

    // 항공권 계산
    private Money calculateFee(int peopleCount){
        return Money.ZERO;
    }
}







