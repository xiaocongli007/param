ALTER TABLE `strategy_types`
    ADD COLUMN `special_workdays` VARCHAR(255) DEFAULT NULL AFTER `special_holidays`;


ALTER TABLE `strategy_types`
    ADD COLUMN `start_time` DATE DEFAULT NULL AFTER `special_workdays`,
    ADD COLUMN `end_time` DATE DEFAULT NULL AFTER `start_time`;


INSERT INTO `response_messages` (`code`, `message`) VALUES
                                                           ('STRATEGY_EXPIRED', '策略已经过期，无法购买');


