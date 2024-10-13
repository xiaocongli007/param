package com.pj.dto;

import org.jetbrains.annotations.NotNull;



public class ProductSaleRuleDTO {

    private Long id;

    private String productCode;

    private Long customerTypeId;


    private Double purchaseLimit;

    private Long strategyTypeId;

    public ProductSaleRuleDTO(Long id, String productCode, Long customerTypeId, Double purchaseLimit, Long strategyTypeId) {
        this.id=id;
        this.productCode = productCode;
        this.customerTypeId = customerTypeId;
        this.purchaseLimit = purchaseLimit;
        this.strategyTypeId = strategyTypeId;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(Long customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public Double getPurchaseLimit() {
        return purchaseLimit;
    }

    public void setPurchaseLimit(Double purchaseLimit) {
        this.purchaseLimit = purchaseLimit;
    }

    public Long getStrategyTypeId() {
        return strategyTypeId;
    }

    public void setStrategyTypeId(Long strategyTypeId) {
        this.strategyTypeId = strategyTypeId;
    }
}