package com.airline.reserve.domain;

import com.airline.reserve.common.Money;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RateDiscountPolicy implements DiscountPolicy {
    @Override
    public Money calculateDiscountAmount(Flight flight) {
        // TODO 비율 할인 정책 구현
        return Money.ZERO;
    }
}
