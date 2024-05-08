package com.airline.reserve.domain;

import com.airline.reserve.common.Money;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AmountDiscountPolicy implements  DiscountPolicy {
    @Override
    public Money calculateDiscountAmount(Flight flight) {
        // TODO 고정 금액할인 구현
        return Money.ZERO;
    }
}
