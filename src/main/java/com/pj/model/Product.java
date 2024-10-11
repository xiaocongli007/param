package com.pj.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product implements Serializable { // 实现 Serializable 接口

    private static final long serialVersionUID = 1L; // 添加 serialVersionUID

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", unique = true, nullable = false)
    private String productCode;

    @Column(name = "product_name", unique = true, nullable = false)
    private String productName;

    @Column(name = "sale_start_date", nullable = false)
    private LocalDate saleStartDate;

    @Column(name = "sale_end_date", nullable = false)
    private LocalDate saleEndDate;

    @Column(name = "total_issued", nullable = false)
    private Double totalIssued;

    @Column(name = "status", nullable = false)
    private String status; // 例如："published" 或 "unpublished"

    // Constructors
    public Product() {}

    public Product(String productCode, String productName, LocalDate saleStartDate, LocalDate saleEndDate, Double totalIssued, String status) {
        this.productCode = productCode;
        this.productName = productName;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
        this.totalIssued = totalIssued;
        this.status = status;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}