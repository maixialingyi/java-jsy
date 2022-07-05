package com.jsy.learn.exception.http.exception;

/**
 * 本系统功能划分,作为错误码Code的前缀
 */
public enum BizType {

    LECHECK_OPEN_ACCOUNT("10","开户"),
    LECHECK_AVAILABLE("20","买单支付是否可用"),
    LECHECK_INSTALLMENT_AVAILABLE("21","买单交易分期是否可用"),
    LECHECK_PAY("30","买单支付"),
    LECHECK_QUERY_PAY_RESULT("31","查询买单支付结果"),
    CREDITPAY_QUERY("32", "信用付查询"),
    LECHECK_ALLOWANCE("33", "买单支付后发零花"),
    CREDITPAY_USER_VALIDATE("34", "用户登录态验证"),
    CREDITPAY_USER_CUSTOMER_VALIDATE("35", "用户客户一致性验证"),
    LECHECK_REPAY("40","主动还款"),
    INSTALLMENT_REPAY("41","主动还款"),
    INSTALLMENT_CORPORATE_REPAY("42", "分期对公还款"),
    LECHECK_CORPORATE_REPAY("44", "买单对公还款"),
    LECHECK_DEDUCT("45","代扣还款"),
    LECHECK_REFUND_GOODS("50","退款"),
    LECHECK_UNREGISTER("70","买单注销"),
    LECHECK_CASH_WITHDRAWAL("80","买单提现"),
    LECHECK_DISPLAY("90","展示"),
    LECHECK_UPGRADE("60","小买单升级"),
    LECHECK_ADMIN("99","买单运营后台"),
    LECHECK_USE_CENTER("65","用户设置"),
    LECHECK_FIRST_CONFIG("66","设置买单优先"),

    ;

    private String bizType;
    private String desc;

    BizType(String bizType, String desc) {
        this.bizType = bizType;
        this.desc = desc;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
