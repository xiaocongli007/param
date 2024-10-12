package com.pj.dto;

import java.time.LocalDate;

/**
 * 策略类型 DTO
 */
public class StrategyTypeDTO {
    private Long id;
    private String strategyCode;
    private String regularHolidays; // 格式："6|7"
    private String specialHolidays; // 格式："20240101|20240102"
    private String specialWorkdays; // 格式："20240501|20240502"
    private LocalDate startTime; // 策略开始日期
    private LocalDate endTime; // 策略结束日期

    // Constructors
    public StrategyTypeDTO() {}

    public StrategyTypeDTO(Long id, String strategyCode, String regularHolidays, String specialHolidays, String specialWorkdays, LocalDate startTime, LocalDate endTime) {
        this.id = id;
        this.strategyCode = strategyCode;
        this.regularHolidays = regularHolidays;
        this.specialHolidays = specialHolidays;
        this.specialWorkdays = specialWorkdays;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public StrategyTypeDTO(String strategyCode, String regularHolidays, String specialHolidays, String specialWorkdays, LocalDate startTime, LocalDate endTime) {
        this.strategyCode = strategyCode;
        this.regularHolidays = regularHolidays;
        this.specialHolidays = specialHolidays;
        this.specialWorkdays = specialWorkdays;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getStrategyCode() {
        return strategyCode;
    }

    public void setStrategyCode(String strategyCode) {
        this.strategyCode = strategyCode;
    }

    public String getRegularHolidays() {
        return regularHolidays;
    }

    public void setRegularHolidays(String regularHolidays) {
        this.regularHolidays = regularHolidays;
    }

    public String getSpecialHolidays() {
        return specialHolidays;
    }

    public void setSpecialHolidays(String specialHolidays) {
        this.specialHolidays = specialHolidays;
    }

    public String getSpecialWorkdays() {
        return specialWorkdays;
    }

    public void setSpecialWorkdays(String specialWorkdays) {
        this.specialWorkdays = specialWorkdays;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public void setId(Long id) {
        this.id = id;
    }
}