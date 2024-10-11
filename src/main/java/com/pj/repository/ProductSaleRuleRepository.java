package com.pj.repository;

import com.pj.model.Product;
import com.pj.model.CustomerType;
import com.pj.model.ProductSaleRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductSaleRuleRepository extends JpaRepository<ProductSaleRule, Long> {
    Optional<ProductSaleRule> findByProductAndCustomerType(Product product, CustomerType customerType);
}