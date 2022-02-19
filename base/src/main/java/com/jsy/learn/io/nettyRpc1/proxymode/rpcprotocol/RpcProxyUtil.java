package com.jsy.learn.io.nettyRpc1.proxymode.rpcprotocol;

import com.jsy.learn.io.nettyRpc1.rpcenv.PackageMsg;
import com.jsy.learn.io.nettyRpc1.rpcenv.connectpool.ClientFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;
import static com.jsy.learn.io.nettyRpc1.proxymode.rpcprotocol.RpcContent.createContent;

public class RpcProxyUtil {
    public static <T>T proxyGet(Class<T>  interfaceInfo){
        ClassLoader loader = interfaceInfo.getClassLoader();
        Class<?>[] methodInfo = {interfaceInfo};

        return (T) Proxy.newProxyInstance(loader, methodInfo, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                /**
                 * 1.获取接口名,方法名,参数类型,返回类型,参数值,Feign dubbo注解自定义属性(如服务别名)
                 * 2.封装协议体
                 * 3.通过服务别名从连接池,负载均衡获取channel,编码
                 * 4.同步等待数据返回
                 * 5.一次解码处理粘包粘包,二次解码序列化
                 * 6.通过requestId,结束同步等待,程序向下执行
                 */
                String name = interfaceInfo.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                //业务协议体body
                RpcContent content = createContent(name, methodName, args, parameterTypes);
                //通信协议头header及requestID及body体
                PackageMsg packageMsg = PackageMsg.requestHeader(content);
                CompletableFuture<Object> cf = ClientFactory.sendMsg(packageMsg,interfaceInfo);
                return cf.get() ;
            }
        });
    }

}

