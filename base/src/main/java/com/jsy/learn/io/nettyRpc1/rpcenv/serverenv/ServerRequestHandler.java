package com.jsy.learn.io.nettyRpc1.rpcenv.serverenv;


import com.jsy.learn.io.nettyRpc1.proxymode.rpcprotocol.RpcContent;
import com.jsy.learn.io.nettyRpc1.rpcenv.PackageMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: 马士兵教育
 * @create: 2020-07-18 11:36
 */
class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    //provider:
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        PackageMsg pkg = (PackageMsg) msg;

        String IOReadThreadName = Thread.currentThread().getName();
        //由于水平触发,io读取完成后才可进行select 此处为自己注册的读写 select 线程
        //ctx.executor().parent().next() 获取其他worker线程, 比如创建了5个worker线程,但有一些监听事件并不频繁,则可用其他线程
        ctx.executor().parent().next().execute(() -> {
            String executeThreadName = Thread.currentThread().getName();
            String s = "io in :" + IOReadThreadName + " execute in :" + executeThreadName + " content.methodName :" + pkg.getContent().getMethodName() + " args :" + pkg.getContent().getArgs()[0];
            PackageMsg rpkg = PackageMsg.responsHeader(pkg.getMessageID(), new RpcContent(s));
            ctx.writeAndFlush(rpkg.getByteBuf());
        });


    }

}
