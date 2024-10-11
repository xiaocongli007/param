package com.pj.dto;

import java.time.LocalDate;

public class ProductDTO {
    private String productCode;
    private String productName;
    private LocalDate saleStartDate;
    private LocalDate saleEndDate;
    private Double totalIssued;
    private String status;

    // Getters and Setters

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