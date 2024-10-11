package com.pj.service;

import java.time.LocalDate;

public class Demo {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        String dateStr = date.format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE);
        System.out.println(dateStr);
    }

}
