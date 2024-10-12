package com.pj.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_sale_rules")
public class ProductSaleRule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "customer_type_id", nullable = false)
    private Long customerTypeId;

    @Column(name = "purchase_limit", nullable = false)
    private Double purchaseLimit;

    @Column(name = "strategy_type_id", nullable = false)
    private Long strategyTypeId;

    // Constructors
    public ProductSaleRule() {}

    public ProductSaleRule(String productCode, Long customerTypeId, Double purchaseLimit, Long strategyTypeId) {
        this.productCode = productCode;
        this.customerTypeId = customerTypeId;
        this.purchaseLimit = purchaseLimit;
        this.strategyTypeId = strategyTypeId;
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