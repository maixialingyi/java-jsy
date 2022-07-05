package com.jsy.learn.zxadapter.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RequestFactory {

    @Autowired
    private List<AbstractRequest> requestList;

    private static final Map<String, Class<? extends AbstractRequest>> REQUEST_MAP = new HashMap<>();

    @PostConstruct
    public void init() {
        for (AbstractRequest request : requestList) {
            String mapKey = request.getSupplierAlias()+"-"+request.getServiceAlias()+"-"+request.getActionAlias();
            REQUEST_MAP.put(mapKey,request.getClass());
        }
    }

    public static Class getRequestClass(String mapKey){
        return REQUEST_MAP.get(mapKey);
    }
}
