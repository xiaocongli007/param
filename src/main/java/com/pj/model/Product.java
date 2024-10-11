package com.pj.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product_type", unique=true, nullable=false)
    private String productType;

    @Column(name="sale_start_date", nullable=false)
    private LocalDate saleStartDate;

    @Column(name="sale_end_date", nullable=false)
    private LocalDate saleEndDate;

    @Column(name="total_issued")
    private Double totalIssued;

    // Constructors
    public Product() {}

    public Product(String productType, LocalDate saleStartDate, LocalDate saleEndDate, Double totalIssued) {
        this.productType = productType;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
        this.totalIssued = totalIssued;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    // No Setter for ID as it's auto-generated

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public LocalDate getSaleStartDate() {
        return saleStartDate;
    }

    public void setSaleStartDate(LocalDate saleStartDate) {
        this.saleStartDate = saleStartDate;
    }

    public LocalDate getSaleEndDate() {
        return saleEndDate;
    }

    public void setSaleEndDate(LocalDate saleEndDate) {
        this.saleEndDate = saleEndDate;
    }

    public Double getTotalIssued() {
        return totalIssued;
    }

    public void setTotalIssued(Double totalIssued) {
        this.totalIssued = totalIssued;
    }
}