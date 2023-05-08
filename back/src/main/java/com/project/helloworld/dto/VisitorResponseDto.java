package com.project.helloworld.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class VisitorResponseDto {

    @Getter
    @Setter
    @Builder
    public static class WeeklyTodayInfo{
        private LocalDate date;
        private Long today;
    }
}
