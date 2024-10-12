package com.pj.dto;

/**
 * 客户类型 DTO
 */
public class CustomerTypeDTO {
    private Long id;
    private String typeName;

    // Constructors
    public CustomerTypeDTO() {}

    public CustomerTypeDTO(Long id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public CustomerTypeDTO(String typeName) {
        this.typeName = typeName;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}