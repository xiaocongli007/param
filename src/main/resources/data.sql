--创建参数表
CREATE TABLE `parameters` (

                              `id` BIGINT NOT NULL AUTO_INCREMENT,

                              `param_key` VARCHAR(255) NOT NULL,

                              `param_value` VARCHAR(255) NOT NULL,

                              `start_time` DATETIME NULL,

                              `end_time` DATETIME NULL,

                              `enabled` BOOLEAN NOT NULL DEFAULT TRUE,

                              PRIMARY KEY (`id`),

                              UNIQUE INDEX `uk_param_key` (`param_key`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



--创建产品表
CREATE TABLE `products` (

                            `id` BIGINT NOT NULL AUTO_INCREMENT,

                            `product_type` VARCHAR(255) NOT NULL,

                            `sale_start_date` DATE NOT NULL,

                            `sale_end_date` DATE NOT NULL,

                            `total_issued` DECIMAL(15,2) DEFAULT NULL,

                            PRIMARY KEY (`id`),

                            UNIQUE INDEX `uk_product_type` (`product_type`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--创建用户表
CREATE TABLE `customer_types` (

                                  `id` BIGINT NOT NULL AUTO_INCREMENT,

                                  `type_name` VARCHAR(255) NOT NULL,

                                  PRIMARY KEY (`id`),

                                  UNIQUE INDEX `uk_type_name` (`type_name`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;



--创建产品销售规则表
CREATE TABLE `product_sale_rules` (

                                      `id` BIGINT NOT NULL AUTO_INCREMENT,

                                      `product_id` BIGINT NOT NULL,

                                      `customer_type_id` BIGINT NOT NULL,

                                      `purchase_limit` DECIMAL(15,2) NOT NULL,

                                      `allowed_on_weekends` BOOLEAN NOT NULL,

                                      PRIMARY KEY (`id`),

                                      FOREIGN KEY (`product_id`) REFERENCES `products`(`id`) ON DELETE CASCADE,

                                      FOREIGN KEY (`customer_type_id`) REFERENCES `customer_types`(`id`) ON DELETE CASCADE,

                                      UNIQUE INDEX `uk_product_customer` (`product_id`, `customer_type_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 插入产品
INSERT INTO `products` (`product_type`, `sale_start_date`, `sale_end_date`, `total_issued`) VALUES
                                                                                                ('特殊国债', '2024-11-01', '2024-11-03', 10000000.00),
                                                                                                ('普通国债', '2055-01-01', '2055-12-31', 50000000.00);

-- 插入客户类型
INSERT INTO `customer_types` (`type_name`) VALUES
                                               ('对公客户'),
                                               ('对私客户');

-- 获取产品和客户类型ID
SET @product_id = (SELECT id FROM `products` WHERE `product_type` = '特殊国债');
SET @customer_type_public = (SELECT id FROM `customer_types` WHERE `type_name` = '对公客户');
SET @customer_type_private = (SELECT id FROM `customer_types` WHERE `type_name` = '对私客户');

-- 插入产品发售规则
INSERT INTO `product_sale_rules` (`product_id`, `customer_type_id`, `purchase_limit`, `allowed_on_weekends`) VALUES
                                                                                                                 (@product_id, @customer_type_public, 500000.00, FALSE),
                                                                                                                 (@product_id, @customer_type_private, 50000.00, TRUE);
