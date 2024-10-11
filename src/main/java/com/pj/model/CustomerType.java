package com.pj.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "customer_types")
public class CustomerType implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}