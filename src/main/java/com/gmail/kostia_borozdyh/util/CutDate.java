package com.gmail.kostia_borozdyh.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CutDate {

    public static String cut(Date date) {
        if (date == null) {
            return null;
        }
        return Calculate.convertToLocalDate(date).toString();
    }
}
