package com.jsy.learn.io.nettyRpc1.rpcenv;

import com.jsy.learn.io.nettyRpc1.packmode.Myheader;
import com.jsy.learn.io.nettyRpc1.proxymode.rpcprotocol.RpcContent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class DecodeHandler extends ByteToMessageDecoder {

    /**
     * 拆包/粘包: java线程发送数据,调用系统,TCP根据滑动窗口大小决定数据包大小
     * 第一次读取,从第一个字节 -> 定长header 读取header,body长度判断 小于可读 读取 大于可读 不读  放入自定义buf中
     * 下次读取 + 上次缓存的字节  判断头长,body长
     *
     * ByteToMessageDecoder 处理了剩余不读字节,
     *
     *
     *
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        //todo 对象头目前是110个字节,应参照dubbo用参数方式传递
        while (buf.readableBytes() >= 110) {
            byte[] bytes = new byte[110];
            buf.getBytes(buf.readerIndex(), bytes); //从哪里读取，读多少，但是buf readindex不变

            //序列化  此处多种实现方式
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oin = new ObjectInputStream(in);
            Myheader header = (Myheader) oin.readObject();

            //如果buf中未读长度去掉header长度,大于数据长度,则读取数据,不够直接返回
            if ((buf.readableBytes() - 110) >= header.getDataLen()) {
                buf.readBytes(110);     //header已取出但未移动指针,此处移动指针
                byte[] data = new byte[header.getDataLen()];
                buf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);
                if(header.getFlag()==0x14141414){
                    RpcContent content = (RpcContent) doin.readObject();
                    //返序列化后对象,进入下一个papline
                    out.add(new PackageMsg(header.getRequestID(),header,content));
                }else{
                    RpcContent content = (RpcContent) doin.readObject();
                    out.add(new PackageMsg(header.getRequestID(),header,content));
                }
            } else {
                break;
            }
        }
    }
}
