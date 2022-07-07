package com.jsy.learn.meituan.http.exception;

public class LecheckIllegalArgumentException extends BaseBizException {


    public LecheckIllegalArgumentException(String message) {
        super(BizCode.LECHECK_ILLEGAL_ARGUMENT, message);
    }

    @Override
    public String getCode() {
        return bizCode.getCode(BizType.LECHECK_QUERY_PAY_RESULT);
    }
}
