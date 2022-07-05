package com.jsy.learn.exception.http.controller;

import com.mid.catexception.adapter.http.exception.BizCode;
import com.mid.catexception.adapter.http.exception.PayBizException;
import com.mid.catexception.adapter.util.Result;
import com.mid.catexception.service.ITestExceptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "全局异常测试")
@RestController("/testException")
public class TestExceptionController {

    @Autowired
    private ITestExceptionService iTestExceptionService;

    @ApiOperation(value = "测试兜底异常")
    @GetMapping(value = "/test1")
    public Result testException() {
        int i = 1/0;
        return Result.success(1);
    }

    @ApiOperation(value = "测试业务异常")
    @GetMapping(value = "/testPayBizException")
    public Result testPayBizException() throws PayBizException {
        if(true){
            throw new PayBizException(BizCode.LECHECK_DISABLED);
        }
        return Result.success(1);
    }

}
