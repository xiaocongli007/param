package com.pj.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "strategy_types")
public class StrategyType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="strategy_code", unique=true, nullable=false)
    private String strategyCode;

    @Column(name="regular_holidays")
    private String regularHolidays;

    @Column(name="special_holidays")
    private String specialHolidays;

    @Column(name="special_workdays")
    private String specialWorkdays; // 新增字段，格式同 regular_holidays

    @Column(name="start_time")
    private LocalDate startTime; // 策略开始日期

    @Column(name="end_time")
    private LocalDate endTime; // 策略结束日期


    // Constructors
    public StrategyType() {}

    public StrategyType(String strategyCode, String regularHolidays, String specialHolidays) {
        this.strategyCode = strategyCode;
        this.regularHolidays = regularHolidays;
        this.specialHolidays = specialHolidays;
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