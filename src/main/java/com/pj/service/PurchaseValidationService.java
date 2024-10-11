package com.pj.service;

import com.pj.dto.PurchaseRequestDTO;
import com.pj.dto.PurchaseResponseDTO;
import com.pj.model.CustomerType;
import com.pj.model.Product;
import com.pj.model.ProductSaleRule;
import com.pj.repository.CustomerTypeRepository;
import com.pj.repository.ProductRepository;
import com.pj.repository.ProductSaleRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PurchaseValidationService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private ProductSaleRuleRepository productSaleRuleRepository;

    public PurchaseResponseDTO validatePurchase(PurchaseRequestDTO request) {
        // 1. 验证产品是否存在
        Optional<Product> optionalProduct = productRepository.findByProductType(request.getProductType());
        if (!optionalProduct.isPresent()) {
            return new PurchaseResponseDTO(false, "产品不存在");
        }
        Product product = optionalProduct.get();

        // 2. 验证购买日期是否在发售日期范围内
        LocalDate purchaseDate = request.getPurchaseDate();
        if (purchaseDate.isBefore(product.getSaleStartDate()) || purchaseDate.isAfter(product.getSaleEndDate())) {
            return new PurchaseResponseDTO(false, "非发售日期");
        }

        // 3. 验证客户类型是否存在
        Optional<CustomerType> optionalCustomerType = customerTypeRepository.findByTypeName(request.getCustomerType());
        if (!optionalCustomerType.isPresent()) {
            return new PurchaseResponseDTO(false, "客户类型不存在");
        }
        CustomerType customerType = optionalCustomerType.get();

        // 4. 获取对应的发售规则
        Optional<ProductSaleRule> optionalRule = productSaleRuleRepository.findByProductAndCustomerType(product, customerType);
        if (!optionalRule.isPresent()) {
            return new PurchaseResponseDTO(false, "未配置发售规则");
        }
        ProductSaleRule rule = optionalRule.get();

        // 5. 验证是否允许在购买日期购买
        DayOfWeek dayOfWeek = purchaseDate.getDayOfWeek();
        boolean isWeekend = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
        if (isWeekend && !rule.getAllowedOnWeekends()) {
            return new PurchaseResponseDTO(false, "节假日不可购买");
        }

        // 6. 验证购买金额是否在限额内
        if (request.getPurchaseAmount() > rule.getPurchaseLimit()) {
            return new PurchaseResponseDTO(false, "购买金额超限");
        }

        // 7. 如果所有验证通过，允许购买
        return new PurchaseResponseDTO(true, "可以购买");
    }
}