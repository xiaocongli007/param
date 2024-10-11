package com.pj.model;

import javax.persistence.*;

@Entity
@Table(name = "customer_types")
public class CustomerType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="type_name", unique=true, nullable=false)
    private String typeName;

    // Constructors
    public CustomerType() {}

    public CustomerType(String typeName) {
        this.typeName = typeName;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    // No Setter for ID as it's auto-generated

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}