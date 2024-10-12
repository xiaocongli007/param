ALTER TABLE `strategy_types`
    ADD COLUMN `special_workdays` VARCHAR(255) DEFAULT NULL AFTER `special_holidays`;


ALTER TABLE `strategy_types`
    ADD COLUMN `start_time` DATE DEFAULT NULL AFTER `special_workdays`,
    ADD COLUMN `end_time` DATE DEFAULT NULL AFTER `start_time`;