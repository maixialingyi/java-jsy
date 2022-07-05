package com.jsy.learn.zxadapter.controller;

import com.jsy.learn.zxadapter.aspect.AdapterRequset;
import com.jsy.learn.zxadapter.request.Request;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "api")
@RestController("/api")
public class ApiController {

    @ApiOperation(value = "request测试")
    @AdapterRequset
    @PostMapping(value = "/adapterRequest")
    public Object ApiTest(@RequestBody Request request){
        return null;
    }
}
