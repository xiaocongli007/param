package com.pj.repository;

import com.pj.model.Product;
import com.pj.model.CustomerType;
import com.pj.model.ProductSaleRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSaleRuleRepository extends JpaRepository<ProductSaleRule, Long> {
    Optional<ProductSaleRule> findByProductCodeAndCustomerTypeId(String productCode, Long customerTypeId);
    List<ProductSaleRule> findByProductCode(String productCode);


}