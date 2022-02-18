package com.jsy.learn.io.bioNetwork;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author: 马士兵教育
 * @create: 2020-05-17 16:18
 */
public class ClientBIO {

    public static void main(String[] args) {

        try {
            Socket client = new Socket("127.0.0.1",9090);

            client.setSendBufferSize(20);
            client.setTcpNoDelay(true);

            OutputStream out = client.getOutputStream();
            // 控制台输入
            InputStream in = System.in;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            while(true){
                String line = reader.readLine();
                if(line != null ){
                    out.write(line.getBytes());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
