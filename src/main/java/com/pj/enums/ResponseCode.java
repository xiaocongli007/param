package com.pj.enums;

/**
 * 响应提示码枚举
 */
public enum ResponseCode {
    PRODUCT_NOT_FOUND, //产品不存在
    PRODUCT_NOT_PUBLISHED,//产品未发布
    NOT_SALE_DATE,//非发售日期
    CUSTOMER_TYPE_NOT_FOUND,//客户类型不存在
    NO_SALE_RULE,//未配置发售规则
    STRATEGY_NOT_FOUND,//策略类型不存在
    HOLIDAY_NOT_ALLOWED,//节假日不可购买
    PURCHASE_LIMIT_EXCEEDED,//购买金额超限
    CAN_PURCHASE//可以购买
}