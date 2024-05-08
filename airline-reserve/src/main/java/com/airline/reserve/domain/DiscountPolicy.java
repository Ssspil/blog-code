package com.airline.reserve.domain;

import com.airline.reserve.common.Money;

public interface DiscountPolicy {
    Money calculateDiscountAmount(Flight flight);
}
