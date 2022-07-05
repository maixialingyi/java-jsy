package com.jsy.learn.zxadapter.request;

import com.jsy.learn.zxadapter.request.enums.ActionAlias;
import com.jsy.learn.zxadapter.request.enums.ServiceAlias;
import com.jsy.learn.zxadapter.request.enums.SupplierAlias;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class Request {

    /**
     * 供应商别名
     */
    @ApiModelProperty(required = true, notes = "供应商别名")
    private SupplierAlias supplierAlias;

    /**
     * 服务别名
     */
    @ApiModelProperty(required = true, notes = "服务别名")
    private ServiceAlias serviceAlias;

    /**
     * 行为别名
     */
    @ApiModelProperty(required = true, notes = "行为别名")
    private ActionAlias actionAlias;

    /**
     * 租户ID(创建租户时，必然不存在租户ID)
     */
    @ApiModelProperty(notes = "租户ID")
    private String tenantId;

    /**
     * 请求数据Mapping
     */
    @ApiModelProperty(required = true, notes = "请求数据")
    private Map<String, Object> kvMapping;

    /**
     * 请求数据
     */
    @ApiModelProperty(required = true, notes = "请求数据", hidden = true)
    private AbstractRequest request;

}
