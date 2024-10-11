package com.pj.model;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parameters")
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "param_key", unique = true, nullable = false)
    private String key;

    @Column(name = "param_value", nullable = false)
    private String value;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    // 构造方法
    public Parameter() {}

    public Parameter(String key, String value, LocalDateTime startTime, LocalDateTime endTime, Boolean enabled) {
        this.key = key;
        this.value = value;
        this.startTime = startTime;
        this.endTime = endTime;
        this.enabled = enabled;
    }

    // Getters 和 Setters

    public Long getId() {
        return id;
    }

    // ID 无 Setter，因为它是自动生成的

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}