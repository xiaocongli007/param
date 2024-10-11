package com.pj.dto;

public class ProductSaleRuleRequest {
    private String productCode;
    private Long customerTypeId;
    private Double purchaseLimit;
    private Long strategyTypeId;

    // Getters and Setters

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