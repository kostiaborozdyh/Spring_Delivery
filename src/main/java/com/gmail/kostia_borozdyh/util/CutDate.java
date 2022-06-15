package com.gmail.kostia_borozdyh.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CutDate {

    public static String cut(Date date) {
        return (date==null) ? null : Calculate.convertToLocalDate(date).toString();
    }
}
