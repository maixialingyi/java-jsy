package com.jsy.learn.meituan.util;

import com.jsy.learn.meituan.http.exception.BizCode;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Result<T>{

    private String code;
    private String message;
    private T data;

    //系统默认 成功无返回值
    public static <T> Result success() {
        Result re = new Result();
        re.setCode(BizCode.SUCCESS.getCode());
        re.setMessage(BizCode.SUCCESS.getMessage());
        return re;
    }
    //系统默认 成功有返回值
    public static <T> Result success(T data) {
        Result re = new Result();
        re.setCode(BizCode.SUCCESS.getCode());
        re.setMessage(BizCode.SUCCESS.getMessage());
        re.setData(data);
        return re;
    }

    //成功无返回值
    public static <T> Result success(String code,String message) {
        Result re = new Result();
        re.setCode(code);
        re.setMessage(message);
        return re;
    }

    //成功有返回值
    public static <T> Result success(String code,String message,T data) {
        Result re = new Result();
        re.setCode(code);
        re.setMessage(message);
        re.setData(data);
        return re;
    }

    //默认系统错误返回
    public static <T> Result fail() {
        Result re = new Result();
        re.setCode(BizCode.ERROR.getCode());
        re.setMessage(BizCode.ERROR.getMessage());
        re.setData(null);
        return re;
    }

    public static <T> Result fail(String code, String message) {
        Result re = new Result();
        re.setCode(code);
        re.setMessage(message);
        return re;
    }

    public static <T> Result fail(String code, String message, T data) {
        Result re = new Result();
        re.setCode(code);
        re.setMessage(message);
        re.setData(data);
        return re;
    }

}
