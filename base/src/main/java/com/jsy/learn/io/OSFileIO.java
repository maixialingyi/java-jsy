package com.jsy.learn.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class OSFileIO {

    public static String data = "123456789";
    public static String path = "D:\\demo\\out.txt";
    public static void main(String[] args) throws Exception {
        //直接流写
        OSFileIO.testBasicFileIO();
        //加buffer写
        OSFileIO.testBufferedFileIO();
        //
        OSFileIO.testNioBufferedAllocateFileIO();
        //
        OSFileIO.testNioBufferedAllocateDirectFileIO();
        //
        OSFileIO.testNioMmapFileIO();
    }

    //流直接读写 系统调用,用户内核态切换 jvm内存 - 进程内存 - 内核pagecache
    public static void testBasicFileIO() throws Exception {
        long startTime = System.currentTimeMillis();

        File file = new File("D:\\demo\\out.txt");
        FileOutputStream out = new FileOutputStream(file);
        for(int i=0;i<1000000;i++){
            out.write(data.getBytes());
        }
        out.close();

        System.out.println("操作时间"+(System.currentTimeMillis() - startTime));
        file.delete();
    }

    public static void testBufferedFileIO() throws Exception {
        long startTime = System.currentTimeMillis();

        File file = new File(path);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        for(int i=0;i<1000000;i++){
            out.write(data.getBytes());
        }
        out.close();

        System.out.println("操作时间"+(System.currentTimeMillis() - startTime));
        file.delete();
    }

    //nio 普通写 同上
    public static void testNioBufferedAllocateFileIO() throws Exception {
        long startTime = System.currentTimeMillis();

        File file = new File(path);
        //本地buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        FileOutputStream outputStream = new FileOutputStream(file);
        FileChannel outChannel = outputStream.getChannel();

        for(int i=0;i<1000000;i++){
            byteBuffer.clear(); //清空缓存
            byteBuffer.put(data.getBytes(StandardCharsets.UTF_8));
            byteBuffer.flip();//读取模式转换写入模式
            outChannel.write(byteBuffer);
        }
        outChannel.close();
        outputStream.close();

        System.out.println("操作时间"+(System.currentTimeMillis() - startTime));
        file.delete();
    }

    //nio 直接内存
    public static void testNioBufferedAllocateDirectFileIO() throws Exception {
        long startTime = System.currentTimeMillis();

        File file = new File(path);
        //进程buffer 直接内存
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        FileOutputStream outputStream = new FileOutputStream(file);
        FileChannel outChannel = outputStream.getChannel();

        for(int i=0;i<1000000;i++){
            byteBuffer.clear(); //清空缓存
            byteBuffer.put(data.getBytes(StandardCharsets.UTF_8));
            byteBuffer.flip();//读取模式转换写入模式
            outChannel.write(byteBuffer);
        }
        outChannel.close();
        outputStream.close();

        System.out.println("操作时间"+(System.currentTimeMillis() - startTime));
        file.delete();
    }

    //todo 有问题需自己测试
    public static void testNioMmapFileIO() throws Exception {
        long startTime = System.currentTimeMillis();

        File file = new File(path);
        RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
        FileChannel channel = randomAccessFile.getChannel();

        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);
        for(int i=0;i<100000;i++){
            map.put(data.getBytes(StandardCharsets.UTF_8));
            map.force();
        }
        randomAccessFile.close();
        channel.close();

        System.out.println("操作时间"+(System.currentTimeMillis() - startTime));
    }
    public static void testRandomAccessFileWrite() throws  Exception {
        RandomAccessFile raf = new RandomAccessFile(path, "rw");

        //------------流直接读写
        raf.write("hello mashibing\n".getBytes());
        raf.write("hello seanzhou\n".getBytes());
        System.out.println("write------------");

        System.in.read();
        //写到指定偏移
        raf.seek(4);
        raf.write("ooxx".getBytes());

        System.out.println("seek---------");
        System.in.read();

        //--------------mmap映射
        FileChannel rafchannel = raf.getChannel();
        //只有文件有mmap映射 : 堆外和文件映射的   byte  not  objtect
        MappedByteBuffer map = rafchannel.map(FileChannel.MapMode.READ_WRITE, 0, 4096);
        //不用系统调用, 直接写到内核pagechache
        map.put("@@@".getBytes());

        System.out.println("map--put--------");
        System.in.read();

        //map.force(); //  flush


        raf.seek(0);
        ByteBuffer buffer = ByteBuffer.allocate(8192);
        //ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        int read = rafchannel.read(buffer);   //buffer.put()
        System.out.println(buffer);
        buffer.flip();
        System.out.println(buffer);

        for (int i = 0; i < buffer.limit(); i++) {
            Thread.sleep(200);
            System.out.print(((char)buffer.get(i)));
        }
}

    // byteBuffer使用
    public  void whatByteBuffer(){
        //ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        System.out.println("postition: " + buffer.position());
        System.out.println("limit: " +  buffer.limit());
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("mark: " + buffer);

        buffer.put("123".getBytes());

        System.out.println("-------------put:123......");
        System.out.println("mark: " + buffer);

        buffer.flip();   //读写交替

        System.out.println("-------------flip......");
        System.out.println("mark: " + buffer);

        buffer.get();

        System.out.println("-------------get......");
        System.out.println("mark: " + buffer);

        buffer.compact();

        System.out.println("-------------compact......");
        System.out.println("mark: " + buffer);

        buffer.clear();

        System.out.println("-------------clear......");
        System.out.println("mark: " + buffer);

    }
}
