package com.pj.model;

import javax.persistence.*;
import java.io.Serializable;

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
}