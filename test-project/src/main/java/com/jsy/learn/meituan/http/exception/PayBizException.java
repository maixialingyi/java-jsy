package com.jsy.learn.meituan.http.exception;

public class PayBizException extends BaseBizException {

    public PayBizException(BizCode bizCode) {
        super(bizCode);
    }

    public PayBizException(BizCode bizCode, String message) {
        super(bizCode, message);
    }

    public PayBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayBizException(BizCode bizCode, Throwable cause) {
        super(bizCode, cause.getMessage(), cause);
    }

    public PayBizException(BizCode bizCode, String message, Throwable cause) {
        super(bizCode, message, cause);
    }

    @Override
    public String getCode() {
                              //业务线
        return bizCode.getCode(BizType.LECHECK_PAY);
    }
}
