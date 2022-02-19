package com.jsy.learn.io.nettyRpc.proxymode.rpcprotocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class RpcContent implements Serializable {

    String name;
    String methodName;
    Class<?>[] parameterTypes;
    Object[] args;
    String res;

    public RpcContent(String res) {
        this.res = res;
    }

    public static RpcContent createContent(String serviceName, String methodName, Object[] args, Class<?>[] parameterTypes){
        RpcContent content = new RpcContent("send..msg");
        content.setArgs(args);
        content.setName(serviceName);
        content.setMethodName(methodName);
        content.setParameterTypes(parameterTypes);
        return content;
    }

}
