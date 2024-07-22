package com.sw.cafe.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalendarioService {

    public List<LocalDateTime> generateMonthCalendar(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        List<LocalDateTime> days = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            days.add(LocalDateTime.of(year, month, day, now.getHour(), now.getMinute()));
        }
        return days;
    }
}
