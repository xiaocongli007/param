package com.pj.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_sale_rules", uniqueConstraints = @UniqueConstraint(columnNames = {"product_code", "customer_type_id"}))
public class ProductSaleRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 使用 product_code 关联 Product
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_code", referencedColumnName = "product_code")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_type_id", referencedColumnName = "id")
    private CustomerType customerType;

    @Column(name = "purchase_limit", nullable = false)
    private Double purchaseLimit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "strategy_type_id", referencedColumnName = "id")
    private StrategyType strategyType;

    // Constructors
    public ProductSaleRule() {}

    public ProductSaleRule(Product product, CustomerType customerType, Double purchaseLimit, StrategyType strategyType) {
        this.product = product;
        this.customerType = customerType;
        this.purchaseLimit = purchaseLimit;
        this.strategyType = strategyType;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Double getPurchaseLimit() {
        return purchaseLimit;
    }

    public void setPurchaseLimit(Double purchaseLimit) {
        this.purchaseLimit = purchaseLimit;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }
}