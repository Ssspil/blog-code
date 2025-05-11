package com.aoxx.util;

import java.time.LocalDate;

public class DateUtils {
    public static String getToday() {
        return LocalDate.now().toString();
    }
}