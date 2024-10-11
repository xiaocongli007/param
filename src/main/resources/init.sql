
INSERT INTO `products` (`product_code`, `product_name`, `sale_start_date`, `sale_end_date`, `total_issued`, `status`) VALUES
                                                                                                                          ('SB20241101', '特殊国债', '2024-11-01', '2024-11-03', 10000000.00, 'published'),
                                                                                                                          ('NB20550101', '普通国债', '2055-01-01', '2055-12-31', 50000000.00, 'published');


INSERT INTO `customer_types` (`type_name`) VALUES
                                               ('对公客户'),
                                               ('对私客户');

INSERT INTO `strategy_types` (`strategy_code`, `regular_holidays`, `special_holidays`) VALUES
                                                                                           ('策略1', '6|7', NULL), -- 每周六日为常规节假日
                                                                                           ('策略2', NULL, NULL); -- 无常规节假日


SET @product_code_special = 'SB20241101';
SET @customer_type_public = (SELECT id FROM `customer_types` WHERE `type_name` = '对公客户');
SET @customer_type_private = (SELECT id FROM `customer_types` WHERE `type_name` = '对私客户');
SET @strategy1_id = (SELECT id FROM `strategy_types` WHERE `strategy_code` = '策略1');
SET @strategy2_id = (SELECT id FROM `strategy_types` WHERE `strategy_code` = '策略2');

-- 插入产品发售规则
INSERT INTO `product_sale_rules` (`product_code`, `customer_type_id`, `purchase_limit`, `strategy_type_id`) VALUES
                                                                                                                (@product_code_special, @customer_type_public, 500000.00, @strategy1_id),
                                                                                                                (@product_code_special, @customer_type_private, 50000.00, @strategy2_id);