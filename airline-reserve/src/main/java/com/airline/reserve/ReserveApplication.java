package com.airline.reserve;

import com.airline.reserve.common.Client;
import com.airline.reserve.common.Money;
import com.airline.reserve.domain.Flight;
import com.airline.reserve.domain.RateDiscountPolicy;
import com.airline.reserve.domain.Reservation;
import com.airline.reserve.domain.Route;
import org.apache.catalina.util.StringUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ReserveApplication {
	public static void main(String[] args) {

		// 현재 팔고 있는 항공편
		Flight flight = Flight.builder()
				.flightNumber("KE0011")
				.airline("대한항공")
				.fee(Money.wons(1762600))
				.departureDateTime(LocalDateTime.of(2024, 10, 18, 20, 40))
				.arrivalDateTime(LocalDateTime.of(2024, 10, 19, 16, 0))
				.route(new Route("route1", "ICN", "LAX", new RateDiscountPolicy())).build();

		// 해당 항공편 예약자들
		Reservation reserve = flight.reserve(
					new Client("손흥민", 30, "남자"),
					new Client("아이유", 24, "여자"),
					new Client("이상혁", 25, "남자"));

		// 항공권 정보
		System.out.println(reserve);
	}
}
