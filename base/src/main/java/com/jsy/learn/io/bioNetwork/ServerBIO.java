package com.jsy.learn.io.bioNetwork;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBIO {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9090,20);
        System.out.println("ServerSocket 创建成功");

        while (true) {
            Socket socket = serverSocket.accept();//阻塞,直到获取到创建完成的连接
            System.out.println("获取到客户端连接 " + socket.getPort());

            new Thread(() -> {
                try {
                    InputStream in = socket.getInputStream();
                    while(true){
                        byte[] b=new byte[2];
                        int len=in.read(b);     //阻塞,直到内核数据准备完成
                        System.out.println("客户端说："+new String(b,0,len));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
