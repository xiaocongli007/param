package com.pj.dto;

/**
 * 策略类型 DTO
 */
public class StrategyTypeDTO {
    private Long id;
    private String strategyCode;
    private String regularHolidays; // 格式："6|7"
    private String specialHolidays; // 格式："20240101|20240102"
    private String specialWorkdays; // 格式："1|3|5"

    // Constructors
    public StrategyTypeDTO() {}

    public StrategyTypeDTO(Long id, String strategyCode, String regularHolidays, String specialHolidays, String specialWorkdays) {
        this.id = id;
        this.strategyCode = strategyCode;
        this.regularHolidays = regularHolidays;
        this.specialHolidays = specialHolidays;
        this.specialWorkdays = specialWorkdays;
    }

    public StrategyTypeDTO(String strategyCode, String regularHolidays, String specialHolidays, String specialWorkdays) {
        this.strategyCode = strategyCode;
        this.regularHolidays = regularHolidays;
        this.specialHolidays = specialHolidays;
        this.specialWorkdays = specialWorkdays;
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

    public void setId(Long id) {
        this.id = id;
    }
}