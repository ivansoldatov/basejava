package com.ocp.basejava.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate ofStr (String s) {
        String[] str = s.split("-",3);
        return of(Integer.parseInt(str[0]),Month.of(Integer.parseInt(str[1])));
    }

}
