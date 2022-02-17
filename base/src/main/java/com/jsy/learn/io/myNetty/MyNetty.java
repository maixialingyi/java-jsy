package com.jsy.learn.io.myNetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.junit.Test;

import java.net.InetSocketAddress;

/**
 * @author: 马士兵教育
 * @create: 2020-06-30 20:02
 */
public class MyNetty {

    /**
     * netty的实现ByteBuf
     */
    @Test
    public void myBytebuf(){

//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(8, 20);
        //pool
//        ByteBuf buf = UnpooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(8, 20);
        print(buf);

        buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
         buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
         buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
         buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
         buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
        buf.writeBytes(new byte[]{1,2,3,4});
        print(buf);
    }

    public static void print(ByteBuf buf){
        System.out.println("buf.isReadable()    :"+buf.isReadable());
        System.out.println("buf.readerIndex()   :"+buf.readerIndex());
        System.out.println("buf.readableBytes() "+buf.readableBytes());
        System.out.println("buf.isWritable()    :"+buf.isWritable());
        System.out.println("buf.writerIndex()   :"+buf.writerIndex());
        System.out.println("buf.writableBytes() :"+buf.writableBytes());
        System.out.println("buf.capacity()  :"+buf.capacity());
        System.out.println("buf.maxCapacity()   :"+buf.maxCapacity());
        System.out.println("buf.isDirect()  :"+buf.isDirect());
        System.out.println("--------------");
    }

    @Test
    public void loopExecutor() throws Exception {
        //group  线程池
        NioEventLoopGroup selector = new NioEventLoopGroup(2);
        selector.execute(()->{
            try {
                for (;;){
                    System.out.println("hello world001");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        selector.execute(()->{
            try {
                for (;;){
                    System.out.println("hello world002");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        System.in.read();
    }

    /**
     * 客户端测试 一
     */
    @Test
    public void clientMode() throws Exception {
        NioEventLoopGroup thread = new NioEventLoopGroup(1);
        NioSocketChannel client = new NioSocketChannel();
        thread.register(client);  //epoll_ctl(5,ADD,3)

        //响应式：基于读写事件调用
        ChannelPipeline p = client.pipeline();
        //配置可读写事件时处理类
        p.addLast(new MyInHandler());

        //reactor  异步的特征
        ChannelFuture connect = client.connect(new InetSocketAddress("192.168.150.11", 9090));
        ChannelFuture sync = connect.sync();

        ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        sync.channel().closeFuture().sync();

        System.out.println("client over....");

    }

    /**
     * 客户端测试二
     * @throws InterruptedException
     */
    @Test
    public void nettyClient() throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(group)
                .channel(NioSocketChannel.class)
                //.handler(new ChannelInit())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyInHandler());
                    }
                })
                .connect(new InetSocketAddress("192.168.150.11", 9090));

        Channel client = connect.sync().channel();

        ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes());
        ChannelFuture send = client.writeAndFlush(buf);
        send.sync();

        client.closeFuture().sync();
    }

    /**
     * 服务端测试一
     */
    @Test
    public void serverMode() throws Exception {

        NioEventLoopGroup thread = new NioEventLoopGroup(1);
        NioServerSocketChannel server = new NioServerSocketChannel();
        thread.register(server);
        //响应式: 事件就绪后触发调用
        ChannelPipeline p = server.pipeline();
        /**
         * NioServerSocketChannel ChannelPipeline : [MyAcceptHandler]
         * SocketChannel          ChannelPipeline : [ChannelInit,MyInHandler] -> [MyInHandler]
         */
        p.addLast(new MyAcceptHandler(thread,new ChannelInit()));  //accept接收客户端，并且注册到selector
        ChannelFuture bind = server.bind(new InetSocketAddress("192.168.150.1", 9090));

        bind.sync().channel().closeFuture().sync();
        System.out.println("server close....");
    }

    @Test
    public void nettyServer() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        ServerBootstrap bs = new ServerBootstrap();
        ChannelFuture bind = bs.group(group, group)
                .channel(NioServerSocketChannel.class)
                // ChannelInitializer 不处理业务逻辑,channelRegistered注册发生时,读写处理绑定
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new MyInHandler());
                    }
                })
                .bind(new InetSocketAddress("192.168.150.1", 9090));

        bind.sync().channel().closeFuture().sync();
    }
}


class MyAcceptHandler extends ChannelInboundHandlerAdapter {

    private final EventLoopGroup selector;
    private final ChannelHandler handler;

    public MyAcceptHandler(EventLoopGroup thread, ChannelHandler channelInit) {
        this.selector = thread;
        this.handler = channelInit;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server registerd...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //可读事件获取创建完成的SocketChannel
        SocketChannel socketChannel = (SocketChannel) msg;
        //SocketChannel 事件处理链
        ChannelPipeline p = socketChannel.pipeline();
        //绑定SocketChannel注册selector事件,处理handler
        p.addLast(handler); //ChannelInit
        //注册
        selector.register(socketChannel);
    }
}

/**单例*/
@ChannelHandler.Sharable
class ChannelInit extends ChannelInboundHandlerAdapter{

    /** SocketChannel注册Selector事件时触发 -> 绑定SocketChannel读写事件处理handler */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel socketChannel = ctx.channel();
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast(new MyInHandler());   /**SocketChannel::ChannelPipeline[ChannelInit,MyInHandler]*/
        ctx.pipeline().remove(this);    /**每个SocketChannel注册Selector只需发生一次,从其ChannelPipeline处理链中移除*/
    }
}

/**
 * 读写事件处理
 */
class MyInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client  registed...");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 读到的是数据
        ByteBuf buf = (ByteBuf) msg;
//        CharSequence str = buf.readCharSequence(buf.readableBytes(), CharsetUtil.UTF_8);
        CharSequence str = buf.getCharSequence(0,buf.readableBytes(), CharsetUtil.UTF_8);
        System.out.println(str);
        ctx.writeAndFlush(buf);
    }
}