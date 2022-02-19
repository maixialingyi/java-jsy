package com.jsy.learn.io.nettyRpc.packmode;


import com.jsy.learn.io.nettyRpc.rpcenv.PackageMsg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class MsgCallBackMapping {
    static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void  addCallBack(long requestID, CompletableFuture cb){
        mapping.putIfAbsent(requestID, cb);
    }
    public static void runCallBack(PackageMsg req){
        CompletableFuture cf = mapping.get(req.getMessageID());
        cf.complete(req.getContent().getRes());
        removeCB(req.getHeader().getRequestID());
    }

    private static void removeCB(long requestID) {
        mapping.remove(requestID);
    }

}
