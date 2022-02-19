package com.jsy.learn.io.nettyRpc.rpcenv;

import com.jsy.learn.io.nettyRpc.packmode.Myheader;
import com.jsy.learn.io.nettyRpc.proxymode.rpcprotocol.RpcContent;
import com.jsy.learn.io.nettyRpc.util.SerDerTool;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Setter
@Getter
@ToString
public class PackageMsg {

    long messageID;
    byte[] headerByte;
    byte[] contentByte;
    Myheader header;
    RpcContent content;

    PackageMsg(long requesID,Myheader header,RpcContent content){
        this.messageID = requesID;
        this.header = header;
        this.content = content;
    }

    public static PackageMsg requestHeader(RpcContent content){
        int f = 0x14141414;
        long messageID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        return createHeader(f,messageID,content);
    }
    public static PackageMsg responsHeader(long messageID,RpcContent content){
        int f = 0x14141424;
        return createHeader(f,messageID,content);
    }
    private static PackageMsg createHeader( int f,long messageID ,RpcContent content){
        Myheader header = new Myheader();
        header.setFlag(f);
        header.setRequestID(messageID);
        PackageMsg pack = new PackageMsg(messageID,header,content);
        pack.doByte();
        return pack;
    }
    public void doByte(){
        this.contentByte = SerDerTool.objToByte(this.content);
        header.setDataLen(contentByte.length);
        this.headerByte = SerDerTool.objToByte(this.header);
    }
    public ByteBuf getByteBuf() {
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(this.headerByte.length+this.contentByte.length);
        byteBuf.writeBytes(headerByte);
        byteBuf.writeBytes(contentByte);
        return byteBuf;
    }
}
