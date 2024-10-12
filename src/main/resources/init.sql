
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

CREATE TABLE `response_messages` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `code` varchar(255) NOT NULL,
                                     `message` varchar(500) NOT NULL,
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `response_messages` (`code`, `message`) VALUES
                                                        ('PRODUCT_NOT_FOUND', '产品不存在'),
                                                        ('PRODUCT_NOT_PUBLISHED', '产品未发布'),
                                                        ('NOT_SALE_DATE', '非发售日期'),
                                                        ('CUSTOMER_TYPE_NOT_FOUND', '客户类型不存在'),
                                                        ('NO_SALE_RULE', '未配置发售规则'),
                                                        ('STRATEGY_NOT_FOUND', '策略类型不存在'),
                                                        ('HOLIDAY_NOT_ALLOWED', '节假日不可购买'),
                                                        ('NON_HOLIDAY_NOT_ALLOWED', '不可购买，非节假日'),
                                                        ('PURCHASE_LIMIT_EXCEEDED', '购买金额超限'),
                                                        ('CAN_PURCHASE', '可以购买');

CREATE TABLE `products` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `product_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                            `product_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                            `sale_start_date` date NOT NULL,
                            `sale_end_date` date NOT NULL,
                            `total_issued` decimal(15,2) DEFAULT NULL,
                            `status` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'unpublished',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `product_type` (`product_name`),
                            UNIQUE KEY `uk_product_code` (`product_code`),
                            UNIQUE KEY `uk_product_name` (`product_name`),
                            UNIQUE KEY `UK_922x4t23nx64422orei4meb2y` (`product_code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 2. 创建 customer_types 表
CREATE TABLE `customer_types` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `type_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `uk_type_name` (`type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 3. 创建 strategy_types 表
CREATE TABLE `strategy_types` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `strategy_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                                  `regular_holidays` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  `special_holidays` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `strategy_code` (`strategy_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 创建 product_sale_rules 表
CREATE TABLE `product_sale_rules` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `product_code` varchar(255) NOT NULL,
                                      `customer_type_id` bigint(20) NOT NULL,
                                      `purchase_limit` double NOT NULL,
                                      `strategy_type_id` bigint(20) NOT NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `uk_product_code_customer_type` (`product_code`,`customer_type_id`),
                                      UNIQUE KEY `UK3r0hu28dyn0wodyjaldyh66hc` (`product_code`,`customer_type_id`),
                                      KEY `FKrsmexcsmdw0wbo637j9p3bx09` (`customer_type_id`),
                                      KEY `FK8hlepkys1o4ftxa6ifjbjlrtx` (`strategy_type_id`),
                                      CONSTRAINT `FK8hlepkys1o4ftxa6ifjbjlrtx` FOREIGN KEY (`strategy_type_id`) REFERENCES `strategy_types` (`id`),
                                      CONSTRAINT `FKrsmexcsmdw0wbo637j9p3bx09` FOREIGN KEY (`customer_type_id`) REFERENCES `customer_types` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- 5. 创建 parameters 表
CREATE TABLE `parameters` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `param_key` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                              `param_value` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
                              `start_time` datetime DEFAULT NULL,
                              `end_time` datetime DEFAULT NULL,
                              `enabled` tinyint(1) NOT NULL DEFAULT '1',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uk_param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
