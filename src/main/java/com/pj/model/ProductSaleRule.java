package com.pj.model;

import javax.persistence.*;

@Entity
@Table(name = "product_sale_rules", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "customer_type_id"}))
public class ProductSaleRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(optional=false)
    @JoinColumn(name="customer_type_id", referencedColumnName = "id")
    private CustomerType customerType;

    @Column(name="purchase_limit", nullable=false)
    private Double purchaseLimit;

    @Column(name="allowed_on_weekends", nullable=false)
    private Boolean allowedOnWeekends;

    // Constructors
    public ProductSaleRule() {}

    public ProductSaleRule(Product product, CustomerType customerType, Double purchaseLimit, Boolean allowedOnWeekends) {
        this.product = product;
        this.customerType = customerType;
        this.purchaseLimit = purchaseLimit;
        this.allowedOnWeekends = allowedOnWeekends;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    // No Setter for ID as it's auto-generated

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

    public Boolean getAllowedOnWeekends() {
        return allowedOnWeekends;
    }

    public void setAllowedOnWeekends(Boolean allowedOnWeekends) {
        this.allowedOnWeekends = allowedOnWeekends;
    }
}