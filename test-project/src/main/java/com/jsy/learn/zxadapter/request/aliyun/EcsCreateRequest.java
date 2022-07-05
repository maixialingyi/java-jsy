package com.jsy.learn.zxadapter.request.aliyun;

import com.jsy.learn.zxadapter.request.AbstractRequest;
import com.jsy.learn.zxadapter.request.enums.ActionAlias;
import com.jsy.learn.zxadapter.request.enums.ServiceAlias;
import com.jsy.learn.zxadapter.request.enums.SupplierAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Scope("prototype")
@Component
public class EcsCreateRequest extends AbstractRequest {

    public EcsCreateRequest() {
        super(SupplierAlias.aliyun, ServiceAlias.ecs, ActionAlias.create);
    }

    /**
     * 规格
     */
    private String instanceType;

    /**
     * 系统磁盘
     */
    private String systemDisk;

}
