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


--新增节假日策略表
CREATE TABLE `strategy_types` (
                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                  `strategy_code` VARCHAR(255) NOT NULL UNIQUE,
                                  `regular_holidays` VARCHAR(255) DEFAULT NULL,
                                  `special_holidays` VARCHAR(255) DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `strategy_types` (`strategy_code`, `regular_holidays`, `special_holidays`) VALUES
                                                                                           ('策略1', '6|7', NULL),
                                                                                           ('策略2', NULL, NULL);

ALTER TABLE `product_sale_rules`
    ADD COLUMN `strategy_type_id` BIGINT NULL;

-- 获取策略类型ID
SET @strategy1_id = (SELECT id FROM `strategy_types` WHERE `strategy_code` = '策略1');
SET @strategy2_id = (SELECT id FROM `strategy_types` WHERE `strategy_code` = '策略2');

-- 更新 product_sale_rules 表中的 strategy_type_id
UPDATE `product_sale_rules`
SET `strategy_type_id` =
        CASE
            WHEN `customer_type_id` = (SELECT id FROM `customer_types` WHERE `type_name` = '对公客户') THEN @strategy1_id
            WHEN `customer_type_id` = (SELECT id FROM `customer_types` WHERE `type_name` = '对私客户') THEN @strategy2_id
            END
WHERE `product_id` = (SELECT id FROM `products` WHERE `product_type` = '特殊国债');


-- 3d. 将 strategy_type_id 列设为 NOT NULL
ALTER TABLE `product_sale_rules`
    MODIFY COLUMN `strategy_type_id` BIGINT NOT NULL;

-- 3e. 添加外键约束
ALTER TABLE `product_sale_rules`
    ADD CONSTRAINT `fk_strategy_type`
        FOREIGN KEY (`strategy_type_id`) REFERENCES `strategy_types`(`id`) ON DELETE CASCADE;


--新增product状态字段
ALTER TABLE `products`
    ADD COLUMN `status` VARCHAR(50) NOT NULL DEFAULT 'unpublished' AFTER `total_issued`;

UPDATE `products`
SET `status` = 'published'
WHERE `product_type` IN ('特殊国债', '普通国债');

ALTER TABLE `products`
    ADD COLUMN `product_code` VARCHAR(255) NULL AFTER `id`;

UPDATE `products` SET `product_code` = UUID() WHERE `product_code` IS NULL;

ALTER TABLE `products`
    CHANGE COLUMN `product_type` `product_name` VARCHAR(255) NOT NULL;

ALTER TABLE `products`
    MODIFY COLUMN `product_code` VARCHAR(255) NOT NULL,
    ADD UNIQUE KEY `uk_product_code` (`product_code`),
    ADD UNIQUE KEY `uk_product_name` (`product_name`);

ALTER TABLE `product_sale_rules`
    ADD COLUMN `product_code` VARCHAR(255) NULL AFTER `id`;




-- 1. 插入产品
INSERT INTO `products` (`product_code`, `product_name`, `sale_start_date`, `sale_end_date`, `total_issued`, `status`) VALUES
                                                                                                                          ('SB20241101', '特殊国债2', '2024-11-01', '2024-11-03', 10000000.00, 'published'),
                                                                                                                          ('NB20550101', '普通国债2', '2055-01-01', '2055-12-31', 50000000.00, 'published');

-- 2. 插入客户类型
INSERT INTO `customer_types` (`type_name`) VALUES
                                               ('对公客户'),
                                               ('对私客户');

-- 3. 插入策略类型
INSERT INTO `strategy_types` (`strategy_code`, `regular_holidays`, `special_holidays`) VALUES
                                                                                           ('策略1', '6|7', NULL),
                                                                                           ('策略2', NULL, NULL);

-- 4. 获取产品、客户类型和策略类型ID
SET @product_code_special = 'SB20241101';
SET @customer_type_public = (SELECT id FROM `customer_types` WHERE `type_name` = '对公客户');
SET @customer_type_private = (SELECT id FROM `customer_types` WHERE `type_name` = '对私客户');
SET @strategy1_id = (SELECT id FROM `strategy_types` WHERE `strategy_code` = '策略1');
SET @strategy2_id = (SELECT id FROM `strategy_types` WHERE `strategy_code` = '策略2');

-- 5. 插入产品发售规则
INSERT INTO `product_sale_rules` (`product_code`, `customer_type_id`, `purchase_limit`, `strategy_type_id`) VALUES
                                                                                                                (@product_code_special, @customer_type_public, 500000.00, @strategy1_id),
                                                                                                                (@product_code_special, @customer_type_private, 50000.00, @strategy2_id);

SET @product_code_special = 'NC20241101';
SET @customer_type_public = (SELECT id FROM `customer_types` WHERE `type_name` = '对公客户');
SET @customer_type_private = (SELECT id FROM `customer_types` WHERE `type_name` = '对私客户');
SET @strategy1_id = (SELECT id FROM `strategy_types` WHERE `strategy_code` = '策略1');
SET @strategy2_id = (SELECT id FROM `strategy_types` WHERE `strategy_code` = '策略2');

-- 5. 插入产品发售规则
INSERT INTO `product_sale_rules` (`product_code`, `customer_type_id`, `purchase_limit`, `strategy_type_id`) VALUES
                                                                                                                (@product_code_special, @customer_type_public, 500000.00, @strategy1_id),
                                                                                                                (@product_code_special, @customer_type_private, 50000.00, @strategy2_id);