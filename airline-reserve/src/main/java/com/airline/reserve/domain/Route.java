package com.airline.reserve.domain;

import com.airline.reserve.common.Money;

import java.time.Duration;
import java.time.LocalDateTime;


/**
 * Created by rius0918@gmail.com on 2024. 5. 15.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
public class Route {

    // 노선 식별자
    private String routeId;

    // 출발 공항
    private String departureAirport;
    // 도착 공항
    private String arrivalAirport;
    // 할인 정책
    private DiscountPolicy discountPolicy;

    public Route(String routeId, String departureAirport, String arrivalAirport, DiscountPolicy discountPolicy) {
        this.routeId = routeId;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.discountPolicy = discountPolicy;
    }

    // (고정, 비율)할인 방법으로 할인된 해당 노선 가격
    public Money calculateFee(Flight flight) {
        return flight.getFee().minus(discountPolicy.calculateDiscountAmount(flight));
    }
}
