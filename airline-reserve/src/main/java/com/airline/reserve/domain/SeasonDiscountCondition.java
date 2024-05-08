package com.airline.reserve.domain;

public class SeasonDiscountCondition implements DiscountCondition{
    @Override
    public boolean isSatisfiedBy(Flight flight) {
        return false;
    }
}
