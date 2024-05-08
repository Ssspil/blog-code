package com.airline.reserve.domain;

public interface DiscountCondition {
    boolean isSatisfiedBy(Flight flight);
}
