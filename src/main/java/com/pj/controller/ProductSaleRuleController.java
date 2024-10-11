package com.pj.controller;

import com.pj.model.CustomerType;
import com.pj.model.Product;
import com.pj.model.ProductSaleRule;
import com.pj.repository.CustomerTypeRepository;
import com.pj.repository.ProductRepository;
import com.pj.repository.ProductSaleRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-sale-rules")
public class ProductSaleRuleController {

    @Autowired
    private ProductSaleRuleRepository productSaleRuleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    /**
     * 获取所有产品发售规则
     *
     * @return 所有规则列表
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductSaleRule>> getAllRules() {
        List<ProductSaleRule> rules = productSaleRuleRepository.findAll();
        return ResponseEntity.ok(rules);
    }

    /**
     * 根据 productCode 和 customerTypeId 获取单个发售规则
     *
     * @param productCode      产品代码
     * @param customerTypeId   客户类型 ID
     * @return 规则详情或错误信息
     */
    @GetMapping("/getByProductCodeAndCustomerType")
    public ResponseEntity<?> getRuleByProductCodeAndCustomerType(
            @RequestParam String productCode,
            @RequestParam Long customerTypeId) {

        Optional<ProductSaleRule> rule = productSaleRuleRepository.findByProduct_ProductCodeAndCustomerType(productCode,
                customerTypeRepository.findById(customerTypeId).orElse(null));

        if (rule.isPresent()) {
            return ResponseEntity.ok(rule.get());
        } else {
            return ResponseEntity.status(404).body("Product sale rule not found");
        }
    }

    /**
     * 新增产品发售规则
     *
     * @param ruleRequest 包含 productCode, customerTypeId, purchaseLimit, strategyTypeId 的请求体
     * @return 新增的规则详情或错误信息
     */
    @PostMapping("/add")
    public ResponseEntity<?> addRule(@RequestBody ProductSaleRuleRequest ruleRequest) {
        // 校验必填字段
        if (ruleRequest.getProductCode() == null || ruleRequest.getProductCode().trim().isEmpty() ||
                ruleRequest.getCustomerTypeId() == null ||
                ruleRequest.getPurchaseLimit() == null ||
                ruleRequest.getStrategyTypeId() == null) {
            return ResponseEntity.badRequest().body("All fields are required");
        }

        // 查找产品
        Optional<Product> productOpt = productRepository.findByProductCode(ruleRequest.getProductCode());
        if (!productOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        Product product = productOpt.get();

        // 查找客户类型
        Optional<CustomerType> customerTypeOpt = customerTypeRepository.findById(ruleRequest.getCustomerTypeId());
        if (!customerTypeOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Customer type not found");
        }

        CustomerType customerType = customerTypeOpt.get();

        // 检查是否已存在相同的规则
        Optional<ProductSaleRule> existingRule = productSaleRuleRepository.findByProductAndCustomerType(product, customerType);
        if (existingRule.isPresent()) {
            return ResponseEntity.badRequest().body("Product sale rule already exists for this product and customer type");
        }

        // 创建新规则
        ProductSaleRule newRule = new ProductSaleRule(
                product,
                customerType,
                ruleRequest.getPurchaseLimit(),
                // 需要查找 StrategyType，这里假设有一个 strategyTypeRepository
                // 需要注入 StrategyTypeRepository 并查找 StrategyType
                // 示例中略过
                // strategyTypeRepository.findById(ruleRequest.getStrategyTypeId()).orElse(null)
                null // 请根据实际情况设置
        );

        // TODO: 设置 strategyType

        // 保存规则
        ProductSaleRule savedRule = productSaleRuleRepository.save(newRule);
        return ResponseEntity.ok(savedRule);
    }

    /**
     * 更新产品发售规则
     *
     * @param ruleRequest 包含 productCode, customerTypeId, purchaseLimit, strategyTypeId 的请求体
     * @return 更新后的规则详情或错误信息
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateRule(@RequestBody ProductSaleRuleRequest ruleRequest) {
        // 校验必填字段
        if (ruleRequest.getProductCode() == null || ruleRequest.getProductCode().trim().isEmpty() ||
                ruleRequest.getCustomerTypeId() == null) {
            return ResponseEntity.badRequest().body("Product code and customer type ID are required for update");
        }

        // 查找产品
        Optional<Product> productOpt = productRepository.findByProductCode(ruleRequest.getProductCode());
        if (!productOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        Product product = productOpt.get();

        // 查找客户类型
        Optional<CustomerType> customerTypeOpt = customerTypeRepository.findById(ruleRequest.getCustomerTypeId());
        if (!customerTypeOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Customer type not found");
        }

        CustomerType customerType = customerTypeOpt.get();

        // 查找现有规则
        Optional<ProductSaleRule> existingRuleOpt = productSaleRuleRepository.findByProductAndCustomerType(product, customerType);
        if (!existingRuleOpt.isPresent()) {
            return ResponseEntity.status(404).body("Product sale rule not found");
        }

        ProductSaleRule existingRule = existingRuleOpt.get();

        // 更新字段
        if (ruleRequest.getPurchaseLimit() != null) {
            existingRule.setPurchaseLimit(ruleRequest.getPurchaseLimit());
        }
        if (ruleRequest.getStrategyTypeId() != null) {
            // 需要查找 StrategyType，这里假设有一个 strategyTypeRepository
            // 需要注入 StrategyTypeRepository 并查找 StrategyType
            // 示例中略过
            // existingRule.setStrategyType(strategyTypeRepository.findById(ruleRequest.getStrategyTypeId()).orElse(null));
        }

        // TODO: 设置 strategyType

        // 保存更新后的规则
        ProductSaleRule updatedRule = productSaleRuleRepository.save(existingRule);
        return ResponseEntity.ok(updatedRule);
    }

    /**
     * 删除产品发售规则
     *
     * @param productCode    产品代码
     * @param customerTypeId 客户类型 ID
     * @return 删除结果或错误信息
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRule(@RequestParam String productCode, @RequestParam Long customerTypeId) {
        // 查找产品
        Optional<Product> productOpt = productRepository.findByProductCode(productCode);
        if (!productOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found");
        }

        Product product = productOpt.get();

        // 查找客户类型
        Optional<CustomerType> customerTypeOpt = customerTypeRepository.findById(customerTypeId);
        if (!customerTypeOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Customer type not found");
        }

        CustomerType customerType = customerTypeOpt.get();

        // 查找规则
        Optional<ProductSaleRule> ruleOpt = productSaleRuleRepository.findByProductAndCustomerType(product, customerType);
        if (!ruleOpt.isPresent()) {
            return ResponseEntity.status(404).body("Product sale rule not found");
        }

        // 删除规则
        productSaleRuleRepository.delete(ruleOpt.get());
        return ResponseEntity.ok("Product sale rule deleted successfully");
    }

    // 定义一个内部类或单独创建一个 DTO 类来接收请求体
    public static class ProductSaleRuleRequest {
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
}