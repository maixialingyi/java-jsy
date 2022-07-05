package com.jsy.learn.exception.http.exception;

/**
 * 业务异常编码
 * 共4位，前两位为系统编号，最后两位为业务场景编号
 * Bizcode在biz层和2位的BizType聚合后，组成最终的业务场景编码，共6位。
 */
public enum BizCode {

    /**
     * 本系统
     */
    SUCCESS("200", "成功"),
    ERROR("500", "系统异常"),
    LECHECK_ILLEGAL_ARGUMENT("0102", "请求参数非法"),
    LECHECK_OTHER_BUSINESS_ERROR("0103", "其他业务异常"),
    LECHECK_DB_VERSION_CONFLICT_ERROR("0198", "买单DB版本号错误"),
    LECHECK_USER_VALIDATE_FAIL("0197", "用户登录态解析失败"),
    LECHECK_USER_VALIDATE_ERROR("0196", "用户登录态解析异常"),
    LECHECK_USER_CUSTOMER_VALIDATE_ERROR("0195", "用户客户验证失败"),
    LECHECK_EXCEEDS_AUTHORIZED_ACCESS("0194", "越权访问"),
    LECHECK_DISABLED("0104", "功能禁用"),


    /**
     * 依赖的客户系统 02
     */
    CUSTOMER_SYSTEM_EXCEPTION("0301", "客户系统异常"),
    CUSTOMER_ILLEGAL_ARGUMENT_EXCEPTION("0302", "接口参数非法"),
    CUSTOMER_NOT_REALNAME_USER("0303", "用户未实名认证"),
    CUSTOMER_REALNAME_CUSTOMER_NOT_EXISTS_EXCEPTION("0304", "不存在消金实名客户信息"),
    CUSTOMER_REALNAME_CUSTOMER_CONFLICT_EXCEPTION("0307", "实名信息不一致"),
    CUSTOMER_SYSTEM_TIME_OUT("0306", "客户系统超时"),

    /**
     * 额度系统 04
     */

    /**
     * 账务系统 05
     */

    /**
     * 合同系统 06
     */
    CONTRACT_SYS_ERROR("0601","合同系统异常"),
    CONTRACT_SYS_TIMEOUT_EXCEPTION("0602", "合同系统接口调用超时"),
    CONTRACT_TYPE_NOT_EXISTS("0603","合同类型不存在"),
    CONTRACT_VERSION_NOT_EXISTS("0605","合同类型对应的版本号为空"),

    /**
     * 风控系统 07
     */
    //查询风控种子白名单信息
    RISK_EXCEPTION("0701", "消金风控系统异常"),
    RISK_TIMEOUT_EXCEPTION("0702", "接口调用超时"),
    RISK_NOT_SEED_WHITELIST_USER("0703", "非种子白名单用户"),
    RISK_SEED_USER_CREDIT_DATA_ERROR("0704", "种子白名单信息中，单笔限额或授信总金额为0或Null"),
    RISK_NAME_LIST_RESULT_SORT_UNKNOWN("0705","风控名单查询结果中的sort字段值非预期"),
    //买单支付风控反欺诈接口
    RISK_TRADE_FRAUD_DETECT_EXCEPTION("0704", "买单支付风控反欺诈~风控系统处理时发生异常"),
    RISK_CASH_LOAN_DELAY_REJECT("0733", "消金风控拒绝-逾期-生活费借钱"),
    RISK_DEFAULT_REJECT("0734", "消金风控拒绝"),
    RISK_INSTALMENT_DELAY_REJECT("0735", "消金风控拒绝-逾期-生活费分期"),
    //支付失败买单是否可用
    RISK_PAY_FAIL_FRAUD_DETECT_REJECT("0736","消金风控拒绝-支付失败风控拒绝"),
    RISK_PAY_FAIL_FRAUD_DETECT_EXCEPTION("0737","消金风控拒绝-支付失败是否展示风控系统异常"),
    RISK_DAILY_LIMIT_REJECT("0758", "消金风控拒绝-达到每日限额"),
    RISK_MONTHLY_LIMIT_REJECT("0759", "消金风控拒绝-达到每月限额"),
    RISK_MERCHANT_REJECT("0760", "消金风控拒绝-商户受限"),
    RISK_HIT_BLACK_LIT_REJECT("0761", "消金风控拒绝-命中用户黑名单"),
    RISK_HIT_FRAUD_RULE_REJECT("0762", "消金风控拒绝-命中欺诈规则"),
    RISK_BAD_QUALIFICATION_REJECT("0763", "消金风控拒绝-资质不良"),

    /**
     * 美团用户中心 08
     */
    //查询用户手机号码 01
    MTUSER_SYSTEM_EXCEPTION("0801", "美团用户中心异常"),
    // mt token无效
    MT_USERCENTER_VALIDATE_EXCEPTION("0802", "美团用户中心校验登陆态异常"),
    // dp token无效
    DP_USERCENTER_VALIDATE_EXCEPTION("0803", "点评用户中心校验登陆态异常"),
    // 不存在dpId到userId的绑定
    MT_USERCENTER_DPUSERID_TO_MTUSERID_EXCEPTION("0804", "根据点评userId查询美团userId异常"),
    // 既不存在dp token 也不存在mt token
    TOKEN_OR_DPER_NO_EXIST("0805", "request未从cookie中获取到token或dper"),

    /**
     * redis 09
     */
    REDIS_LOCK_FAILED("0901", "添加分布式锁失败"),

    /**
     * 支付退款平台 10
     */
    REFUND_MONEY_ERROR("1001", "退款平台退款异常"),

    /**
     * 钱包系统 11
     */
    // 钱包银行卡
    BANK_CARD_QUERY_ERROR("1101", "钱包银行卡查询异常"),
    DEDUCT_CANCEL("1103", "代扣交易撤销"),
    // 钱包站内信
    WALLET_OVER_DUE_PUSH_REVOKE_ERROR("1102", "钱包撤销逾期push异常"),
    BIND_CARD_SYSTEM_EXCEPTION("1103", "钱包银行卡中心系统异常"),

    /**
     * 零花系统 12
     */
    ALLOWANCE_SUCCESS("1200", "成功"),
    // 系统错误
    ALLOWANCE_SYSTEM_ERROR("1201", "系统异常"),
    // 重复提交
    ALLOWANCE_REPEAT_SUBMIT("1202", "重复提交"),
    // 交易不存在
    ALLOWANCE_TRADE_NOT_EXIST("1203", "交易不存在"),
    ALLOWANCE_QUOTA_NOT_ENOUGH("1204", "活动配额不足"),
    ALLOWANCE_NOT_CONFIGURE("1205", "活动还未配置"),
    ALLOWANCE_NOT_START_YET("1206", "活动未开始"),
    ALLOWANCE_IS_OVER("1207", "活动已经结束"),
    ALLOWANCE_DISABLED("1208", "活动已手动关闭"),
    ALLOWANCE_INVALID_PAY_AMOUNT("1209", "无效的支付金额"),
    ALLOWANCE_INVALID_ALLOWANCE_AMOUNT("1210", "无效的零花发放数量"),
    ALLOWANCE_OVER_MONTHLY_LIMIT("1211", "零花发放超过当月限制次数"),

    /**
     * 生活费 借钱 13
     */
    CCPPORTAL_SYSTEM_ERROR("1301","生活费借钱服务系统异常"),

    /**
     * 营销券系统 14
     */
    MARKING_COUPON_SYSTEM_ERROR("1401","营销券系统异常"),
    CCP_COUPON_UNAVAILABLE("1402","券不可用"),
    CCP_COUPON_REQ_TIMEOUT("1403","券系统超时"),
    CCP_COUPON_FREEZE_FAIL("1404","冻结券失败"),
    CCP_COUPON_UNFREEZE_FAIL("1405","解冻券失败"),

    /**
     * 支付收单&会员代扣系统
     */
    PAY_TRADE_ORDER_GEN_ERROR("1501", "生成预支付单异常"),
    PAY_TRADE_ORDER_GEN_RESULT_ERROR("1502", "生成预支付单返回结果异常"),
    PAY_CHECKOFF_RESULT_ERROR("1503", "请求代扣返回结果异常"),
    PAY_CHECKOFF_ERROR("1504", "请求代扣受理异常"),
    PAY_TRADE_ORDER_QUERY_ERROR("1505", "查询交易结果异常"),
    PAY_TRADE_ORDER_QUERY_RESULT_ERROR("1506", "查询交易结果返回失败"),
    PAY_TRADE_ORDER_CLOSE_ERROR("1507", "交易关单异常"),
    PAY_TRADE_ORDER_CLOSE_RESULT_ERROR("1508", "交易关单结果返回失败"),
    PAY_TRADE_ORDER_NOTIFY_RESULT_ERROR("1509", "交易通知结果返回失败"),
    PAY_CHECKOFF_RESULT_NO_BANKCARD_LIST("1510", "请求代扣返回无可用代扣卡"),
    PAY_TRADE_ORDER_REFUND_APPLY_ERROR("1510", "请求退款受理异常"),
    PAY_TRADE_ORDER_REFUND_APPLY_RESULT_ERROR("1511", "请求退款结果返回失败"),

    /**
     * 联名卡系统 16
     */
    COBC_SYSTEM_ERROR("1601","联名卡系统异常"),

    /**
     * 短链系统 17
     */
    SHORT_LINK_ERROR("1701","短链系统异常"),
    MESSAGE_SEND_ERROR("1702", "消息发送异常"),


    /**
     * 钱包代理&会员签约 18
     */
    MWALLET_TWO_ELEMENT_AUTH_ERROR("1801", "二要素认证异常"),
    MWALLET_TWO_ELEMENT_AUTH_RESULT_ERROR("1802", "二要素认证结果异常"),
    MWALLET_REPORT_TWO_ELEMENT_AUTH_ERROR("1803", "上报二要素认证异常"),
    MWALLET_REPORT_TWO_ELEMENT_AUTH_RESULT_ERROR("1804", "上报二要素认证结果异常"),
    MWALLET_SYSTEM_TIME_OUT("1805", "商家钱包系统超时"),
    MWALLET_MEMBER_SIGN_CONTRACT_RESULT_ERROR("1806", "会员代扣签约返回异常"),
    MWALLET_MEMBER_SIGN_CONTRACT_RESULT_FAIL("1807", "会员代扣签约返回失败"),
    MPAY_UNSIGN_ERROR("1810", "会员解约请求异常"),
    MPAY_UNSIGN_RESULT_ERROR("1811", "会员解约请求返回结果失败"),
    MWALLET_RESULT_VERIFY_SIGN_ERROR("1808", "钱包接口结果验签异常"),
    MWALLET_REQUEST_SIGN_ERROR("1810", "钱包接口请求加签异常"),
    MWALLET_SYSTEM_ERROR("1809", "钱包接口系统异常"),

    /**
     * 验证系统 19
     */
    YODA_AUTHORISE_ERROR("1901", "YODA获取requestCode异常"),
    YODA_AUTHORISE_RESULT_ERROR("1902", "YODA获取requestCode结果异常"),
    YODA_RESPONSE_ERROR("1903", "YODA获取校验结果异常"),
    YODA_SYSTEM_TIME_OUT("1904", "YODA系统超时"),


    /**
     * 额度系统 20
     */
    CUSTOMER_LIMIT_SYSTEM_EXCEPTION("2001", "额度系统异常"),
    CUSTOMER_LIMIT_INVALID_PARAM_EXCEPTION("2002", "接口参数异常"),
    CUSTOMER_LIMIT_RESPONSE_ERROR_EXCEPTION("2003", "接口返回结果为空"),
    CUSTOMER_LIMIT_SYSTEM_TIME_OUT_EXCEPTION("2004","额度系统超时"),
    CUSTOMER_LIMIT_BIZSERIALNUMBER_IDEPOMENT_EXCEPTION("2005","交易流水重复"),
    CUSTOMER_LIMIT_ORIGINAL_RECORD_NOT_MATCH_EXCEPTION("2006","原始交易流水号不匹配"),
    CUSTOMER_LIMIT_NOT_USABLE_EXCEPTION("2007","额度信息不可用"),
    CUSTOMER_LIMIT_NOT_ENOUGH_EXCEPTION("2008","额度不足"),
    CUSTOMER_LIMIT_NOT_EXIST_EXCEPTION("2009","额度不存在"),
    CUSTOMER_LIMIT_ALREADY_EXIST_EXCEPTION("2010","额度已存在"),
    CUSTOMER_LIMIT_CREATED_FORBIDDEN_EXCEPTION("2011","用户禁止创建额度"),


    /**
     * 延期还款 21
     */
    HAS_NO_CHARGROFF_BILL("2100","当前用户没有已出账单"),
    HAS_MORE_CHARGROFF_BILL("2101","当前用户存在多笔出账账单，账单数量异常"),
    APPLY_DELAY_AMOUNT_NOT_ALLOW("2102","可延期金额不满足延期金额区间"),
    DELAY_REPAY_RISK_REFUND("2103","延期申请风控拒绝"),
    SERVICE_FEE_GEN_ERROR("2104","延期手续费获取异常"),
    DELAY_REPAY_ACCOUNT_ERROR("2105", "延期还款记账失败"),
    DELAY_ORDER_NOT_EXISTS("2106","延期订单不存在"),
    DELAY_LOAN_BILL_NOT_EXISTS("2107","延期账单不存在"),
    DELAY_LOAN_NOT_OPEN("2108","延期还款未开启"),
    HAS_MORE_OVERDUE_BILL("2109","用户存在多笔逾期"),
    OVERDUE_BILL_NOT_CURRENT_MONTH("2110","逾期账单非当月"),
    NOT_ALLOW_OVERDUE_DELAY("2111","不支持逾期延期，未开启"),
    OVERDUE_DAYS_NOT_ALLOW("2112","逾期天数不在可延期范围"),
    AMOUNT_RANGE_NOT_ALOOW_DELAY_APPLY("2113","手续费区间范围不允许延期"),
    AMOUNT_NOT_IN_SPECIFIED_RANGE("2114","延期金额不在指定区间"),


    /**
     * 消金营销系统 22
     */
    CCP_MARKETING_AD_REQ_EXCEPTION("2201", "请求消金营销广告信息异常"),
    CCP_MARKETING_AD_REQ_TIMEOUT("2202", "请求消金营销广告信息超时异常"),

    /**
     * 账单分期23
     */
    BILL_INSTALLMENT_IS_NOT_OPEN("2300","账单分期还款未开启"),
    ACCOUNT_HAS_OVERDUE_BILL("2301","用户存在逾期账单，不支持账单分期"),
    BILL_INSTALLMENT_RISK_REFUND("2302","账单分期申请风控拒绝"),
    APPLY_BILL_INSTALLMENT_AMOUNT_NOT_ALLOW("2303","账单分期可分期金额不满足分期金额区间"),
    BILL_NOT_BELONG_TO_CURRENT_USER("2304","申请分期的账单不属于当期用户"),
    SERVICE_FEE_INDEX_NOT_EXISTS("2305","期数对应利率索引不存在"),
    ACCOUNT_SYS_REPAY_PLANS_IS_EMPTY("2306","账务系统插叙账单还款计划不存在"),
    BILLINSTALLMENT_REPAY_KEEP_ACCOUNT_ERROR("2307", "账单分期记账失败"),

    /**
     * 触达 24
     */
    NOTIFY_QUERY_DOMAIN_ERROR("2400", "触达过程查询领域模型异常"),

    /**
     * 买单系统准入策略校验 39
     */
    LECHECK_VALIDATE_ACCESSNO_ILLEGAL("3901","接入方标识不合法"),
    LECHECK_VALIDATE_SERVICE_NO_UNIQUE("3902","业务流程的策略校验类不唯一"),
    LECHECK_VALIDATE_SERVICE_NO_EXIST("3903","业务流程的策略校验类不存在"),
    LECHECK_VALIDATE_RULE_PARAM_ILLEGAL("3904","当前Param实例类型与业务准入策略校验规则所需Param实例类型不匹配"),

    /**
     * 管控系统 25
     */
    BLOCK_SYSTEM_EXCEPTION("2501", "账务系统异常"),

    /**
     * 支付侧风控
     */
    PAY_RCFRAUD_WARDEN_EXCEPTION("2601","参数错误，无此方法，异常等"),
    PAY_RCFRAUD_TIME_OUT_EXCEPTION("2602","接口超时"),
    PAY_RCFRAUD_SYSTEM_EXCEPTION("2603","系统异常"),

    /**
     * 提现27
     */
    CASH_WITHDRAWAL_AVAILABLE("2700","取现可用"),
    CASH_WITHDRAWAL_IS_NOT_OPEN("2701","提现功能未开启"),
    NOT_IN_CASH_WITHDRAWAL_NAME_LIST("2702","不在提现名单"),
    HAS_UNSETTLED_CASH_WITHDRAWAL_LOAN("2703","用户存在未结清取现记录"),
    USER_AVAILABLE_LIMIT_NOT_ENOUGH_FOR_CASH_WITHDRAWAL("2704", "用户可用额度不满足取现"),
    CASH_WITHDRAWAL_SERVICE_FEE_CONFIG_IS_NONE("2705","没有获取到手续费配置"),
    CASH_WITHDRAWAL_AMOUNT_IS_NOT_AVAILABLE("2706","取现传入金额不满足300 500规格需求"),
    CASH_WITHDRAWAL_SERVICE_FEE_CONFIG_IS_CHANGE("2707","手续费配置发生变化，与前端传入不一致"),
    CASH_WITHDRAWAL_IS_CONCURRENT("2708","取现并发操作，锁冲突"),
    CASH_WITHDRAWAL_RISK_REJECT("2709","取现风控拒绝"),
    CASH_WITHDRAWAL_PROCESSING("2710","取款处理中"),
    CASH_WITHDRAWAL_KEEP_ACCOUNT_ERROR("2711", "取现还款记账失败"),
    CASH_WITHDRAWAL_STATUS_ERROR("2720", "获取取现状态异常"),
    CASH_WITHDRAWAL_SERVICE_FEE_CONFIG_NOT_FOUND("2712","没有获取到对应区间的手续费"),
    CASH_WITHDRAWAL_REQUEST_AMOUNT_IS_CHANGE("2713","用户申请提现金额发生变化，与前端传入不一致"),


    /**
     * 打款平台28
     */
    PAYMENT_TRANSFER_FAIL("2800","打款受理失败"),
    PAYMENT_TRANSFER_SYSTEM_ERROR_RETRY("2801","付款平台异常，可重试"),
    PAYMENT_TRANSFER_SERVICE_DEGRADE("2802","打款操作被限流"),
    PAYMENT_TRANSFER_SERVICE_TIMEOUT("2803","打款操作接口返回超时"),
    PAYMENT_TRANSFER_QUERY_SYSTEM_ERROR("2804","打款插叙接口异常"),


    /**
     * 综合授信系统29
     */
    UNION_CREDIT_APPLY_EXCEPTION("2901", "综合授信系统异常"),
    UNION_CREDIT_TIMEOUT_EXCEPTION("2902", "综合授信接口调用超时"),
    UNION_CREDIT_APPLY_NOT_ALLOW("2903", "不满足联合开通条件"),

    /**
     * 实名认证系统 30
     */
    AUTH_SYSTEM_EXCEPTION("3001","系统异常"),
    AUTH_TIMEOUT_EXCEPTION("3002","超时异常"),
    TRADE_NOT_EXIST("3003","升级流程不存在"),
    AUTH_FAIL("3004","实名认证失败"),
    UPGRADE_ILLEGAL_CUSTOMER_ACCOUNT_STATUS("3005","账户状态异常不允许升级"),
    UPGRADE_NOT_QUILIFIED("3006","不具备升级资格"),
    UPGRADE_RISK_REJECT("3007","风控拒绝"),
    EXIST_PROCESSING_ORDER("3008","存在进行中的升级流程"),
    RAISE_CREDIT_LITMIT_FAIL("3009","提额失败"),


    /**
     * leaf 31
     */
    LEAF_GET_ID_EXCEPTION("3101","leafId获取异常"),

    /**
     * 状态机 32
     */
    STATE_MACHINE_STATE_FLOW_EXCEPTION("3201","状态机状态流转异常"),

    /**
     * mq 33
     */
    MQ_SEND_MSG_UNKNOW_FAIL("3301","mq消息发送未知原因失败"),

    /**
     * 外卖34
     */
    WAIMAI_TIME_OUT_EXCEPTION("3402","接口超时"),
    WAIMAI_SYSTEM_EXCEPTION("3401","系统异常"),


    /**
     * 转账服务 34
     */
    FUNDS_TRANSFER_SYSTEM_EXCEPTION("3401","转账服务系统异常"),
    FUNDS_TRANSFER_TIME_OUT_EXCEPTION("3402","转账服务超时异常"),
    FUNDS_TRANSFER_TRANSFER_FAIL("3403","转账服务转账失败"),

    /**
     * 资方授信流程相关 35
     */
    FUND_PARTY_APPLY_BILL_NOT_ACCESS("3501","已还账单条件不达标"),
    FUND_PARTY_APPLY_REAL_NAME_INFO_NOT_ACCESS("3502","实名信息不满足"),
    FUND_PARTY_APPLY_SIGED_CONTRACT_NOT_ACCESS("3503","已签合同"),
    FUND_PARTY_APPLY_SIGED_CONTRACT_POPUP_NOT_ACCESS("3504","弹窗频次上限"),
    FUND_PARTY_APPLY_HAS_CREDIT_NOT_ACCESS("3505","客户存在已授信资方"),
    FUND_PARTY_APPLY_GRAY_NOT_ACCESS("3506","客户不符合灰度规则"),
    FUND_PARTY_CHANGE_BILL_DATE_NOT_ACCESS("3507", "客户不符合修改账期规则"),
    FUND_PARTY_APPLY_LOAN_ORDER_NOT_ACCESS("3508","借款信息不符合灰度规则"),
    FUND_PARTY_APPLY_WHITE_LIST_NOT_ACCESS("3509","名单信息不符合灰度规则"),

    /**
     * 资方审批系统 36
     */
    ORGANIZATION_SYSTEM_EXCEPTION("3601","资方审批服务系统异常"),
    ORGANIZATION_TIME_OUT_EXCEPTION("3602","资方审批服务超时异常"),

    /**
     * 资金系统 37
     */
    CAPITAL_ALLOCATION_ILLEGAL_ARGUMENT_EXCEPTION("3701","资金系统请求参数非法"),
    CAPITAL_ALLOCATION_UNSUPPORTED_CUSTOMER_EXCEPTION("3702","资金系统不支持的客户"),
    CAPITAL_ALLOCATION_SYSTEM_EXCEPTION("3703","资金系统系统异常"),
    CAPITAL_ALLOCATION_TIME_OUT_EXCEPTION("3704","资金系统超时异常"),

    /**
     * 文件传输系统 38
     */
    FILE_TRANSFER_ILLEGAL_ARGUMENT_EXCEPTION("3801","文件传输系统参数非法异常"),
    FILE_TRANSFER_SYSTEM_EXCEPTION("3802","文件传输系统系统异常"),
    FILE_TRANSFER_TIME_OUT_EXCEPTION("3803","文件传输系统超时异常"),

    /**
     * 买单运营后台 99
     * **/
    HBASE_QUERY_FAIL("9901", "买单不可用原因查询异常")
    ;


    private String code;
    private String message;

    BizCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getCode(BizType bizType) {
        return bizType.getBizType() + this.code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer codeWithScenes(BizType bizType) {
        return Integer.valueOf(bizType.getBizType() + this.code);
    }

    @Override
    public String toString() {
        return "BizCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
