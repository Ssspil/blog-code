package com.aoxx.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

    @Test
    void getToday_shouldReturnDateString() {
        String today = DateUtils.getToday();
        Assertions.assertThat(today).matches("\\d{4}-\\d{2}-\\d{2}");
    }
}