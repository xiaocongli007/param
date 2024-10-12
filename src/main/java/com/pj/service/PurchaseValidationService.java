package com.pj.service;

import com.pj.dto.PurchaseRequestDTO;
import com.pj.dto.PurchaseResponseDTO;
import com.pj.model.CustomerType;
import com.pj.model.Product;
import com.pj.model.ProductSaleRule;
import com.pj.model.StrategyType;
import com.pj.repository.CustomerTypeRepository;
import com.pj.repository.ProductRepository;
import com.pj.repository.ProductSaleRuleRepository;
import com.pj.repository.StrategyTypeRepository; // 添加此行
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PurchaseValidationService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;

    @Autowired
    private ProductSaleRuleRepository productSaleRuleRepository;

    @Autowired
    private StrategyTypeRepository strategyTypeRepository; // 添加此行

    public PurchaseResponseDTO validatePurchase(PurchaseRequestDTO request) {
        // 1. 验证产品是否存在，通过 productCode
        Optional<Product> optionalProduct = productRepository.findByProductCode(request.getProductCode());
        if (!optionalProduct.isPresent()) {
            return new PurchaseResponseDTO(false, "产品不存在");
        }
        Product product = optionalProduct.get();

        // 验证产品是否已发布
        if (!"published".equalsIgnoreCase(product.getStatus())) {
            return new PurchaseResponseDTO(false, "产品未发布");
        }

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

        // 4. 获取对应的发售规则，通过 productCode 和 customerTypeId
        Optional<ProductSaleRule> optionalRule = productSaleRuleRepository.findByProductCodeAndCustomerTypeId(
                product.getProductCode(), customerType.getId());
        if (!optionalRule.isPresent()) {
            return new PurchaseResponseDTO(false, "未配置发售规则");
        }
        ProductSaleRule rule = optionalRule.get();

        // 5. 根据 strategyTypeId 查询 StrategyType
        Optional<StrategyType> optionalStrategy = strategyTypeRepository.findById(rule.getStrategyTypeId());
        if (!optionalStrategy.isPresent()) {
            return new PurchaseResponseDTO(false, "策略类型不存在");
        }
        StrategyType strategy = optionalStrategy.get();

        // 6. 验证是否允许在购买日期购买
        if (!isAllowedToPurchase(purchaseDate, strategy)) {
            // 根据策略类型决定提示信息
            if (strategy.getRegularHolidays() != null && !strategy.getRegularHolidays().isEmpty()) {
                return new PurchaseResponseDTO(false, "节假日不可购买");
            } else {
                return new PurchaseResponseDTO(false, "不可购买，非节假日");
            }
        }

        // 7. 验证购买金额是否在限额内
        if (request.getPurchaseAmount() > rule.getPurchaseLimit()) {
            return new PurchaseResponseDTO(false, "购买金额超限");
        }

        // 8. 如果所有验证通过，允许购买
        return new PurchaseResponseDTO(true, "可以购买");
    }

    /**
     * 判断是否允许在指定日期购买
     *
     * @param date     购买日期
     * @param strategy 策略类型
     * @return 如果允许购买返回 true，否则返回 false
     */
    private boolean isAllowedToPurchase(LocalDate date, StrategyType strategy) {
        boolean isHoliday = false;

        // 处理常规节假日
        if (strategy.getRegularHolidays() != null && !strategy.getRegularHolidays().isEmpty()) {
            String[] regulars = strategy.getRegularHolidays().split("\\|");
            Set<Integer> regularSet = new HashSet<>();
            for (String s : regulars) {
                try {
                    regularSet.add(Integer.parseInt(s));
                } catch (NumberFormatException e) {
                    // 忽略无效的数字
                }
            }
            isHoliday = regularSet.contains(date.getDayOfWeek().getValue());
        }

        // 处理特殊节假日
        if (!isHoliday && strategy.getSpecialHolidays() != null && !strategy.getSpecialHolidays().isEmpty()) {
            String[] specials = strategy.getSpecialHolidays().split("\\|");
            String dateStr = date.format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE);
            for (String special : specials) {
                if (special.equals(dateStr)) {
                    isHoliday = true;
                    break;
                }
            }
        }

        // 根据节假日情况决定是否允许购买
        if (isHoliday) {
            return false; // 节假日不可购买
        } else {
            return true; // 非节假日可以购买
        }
    }
}