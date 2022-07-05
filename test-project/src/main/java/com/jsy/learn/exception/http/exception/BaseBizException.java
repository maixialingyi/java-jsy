package com.jsy.learn.exception.http.exception;

/**
 * 业务异常  既自定义抛出的异常  如空指针异常等
 */
public abstract class BaseBizException extends Exception implements BaseException{

    /**
     * bizCode没有bizType前缀
     */
    protected BizCode bizCode;

    public BaseBizException(BizCode bizCode) {
        super(bizCode.getMessage());
        this.bizCode = bizCode;
    }

    public BaseBizException(BizCode bizCode, Throwable cause) {
        super(bizCode.getMessage(),cause);
        this.bizCode = bizCode;
    }

    public BaseBizException(BizCode bizCode, String message) {
        super(message);
        this.bizCode = bizCode;
    }

    public BaseBizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseBizException(BizCode bizCode, String message, Throwable cause) {
        super(message, cause);
        this.bizCode = bizCode;
    }



    public BizCode getBizCode() {
        return bizCode;
    }

    public void setBizCode(BizCode bizCode) {
        this.bizCode = bizCode;
    }

    public abstract String getCode() ;

    public String getCodeMessage() {
        return this.bizCode.getMessage();
    }
}
