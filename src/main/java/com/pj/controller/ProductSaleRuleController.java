package com.pj.controller;

import com.pj.dto.ProductSaleRuleDTO;
import com.pj.model.ProductSaleRule;
import com.pj.repository.ProductSaleRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/productSaleRules")
public class ProductSaleRuleController {

    @Autowired
    private ProductSaleRuleRepository productSaleRuleRepository;

    /**
     * 添加新的 ProductSaleRule
     * 通过 POST 方法
     */
    @PostMapping("/add")
    public ResponseEntity<?> addProductSaleRule( @RequestBody ProductSaleRuleDTO dto) {
        // 检查是否已存在相同的规则
        Optional<ProductSaleRule> existingRule = productSaleRuleRepository.findByProductCodeAndCustomerTypeId(
                dto.getProductCode(), dto.getCustomerTypeId());
        if (existingRule.isPresent()) {
            return ResponseEntity.badRequest().body("该产品代码和客户类型的规则已存在。");
        }

        // 创建新的 ProductSaleRule 实体
        ProductSaleRule newRule = new ProductSaleRule();
        newRule.setProductCode(dto.getProductCode());
        newRule.setCustomerTypeId(dto.getCustomerTypeId());
        newRule.setPurchaseLimit(dto.getPurchaseLimit());
        newRule.setStrategyTypeId(dto.getStrategyTypeId());

        // 保存到数据库
        ProductSaleRule savedRule = productSaleRuleRepository.save(newRule);
        return ResponseEntity.ok(savedRule);
    }

    /**
     * 更新现有 ProductSaleRule 的 purchaseLimit 和 strategyTypeId
     * 通过 POST 方法
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateProductSaleRule( @RequestBody ProductSaleRuleDTO dto) {
        // 查找现有的 ProductSaleRule
        Optional<ProductSaleRule> existingRuleOpt = productSaleRuleRepository.findByProductCodeAndCustomerTypeId(
                dto.getProductCode(), dto.getCustomerTypeId());
        if (!existingRuleOpt.isPresent()) {
            return ResponseEntity.status(404).body("ProductSaleRule 未找到。");
        }

        ProductSaleRule existingRule = existingRuleOpt.get();
        existingRule.setPurchaseLimit(dto.getPurchaseLimit());
        existingRule.setStrategyTypeId(dto.getStrategyTypeId());

        // 保存更新后的实体
        ProductSaleRule updatedRule = productSaleRuleRepository.save(existingRule);
        return ResponseEntity.ok(updatedRule);
    }

    // 可选：添加获取、删除等其他接口
}