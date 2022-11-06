package com.nijunyang.nio;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author: create by nijunyang
 * @date:2019/8/19
 */
public class BlockingNIOTest {

    /**
     * 一、使用 NIO 完成网络通信的三个核心：
     * <p>
     * 1. 通道（Channel）：负责连接
     * <p>
     * java.nio.channels.Channel 接口：
     * |--SelectableChannel
     * |--SocketChannel
     * |--ServerSocketChannel
     * |--DatagramChannel
     * <p>
     * |--Pipe.SinkChannel
     * |--Pipe.SourceChannel
     * <p>
     * 2. 缓冲区（Buffer）：负责数据的存取
     * <p>
     * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
     * <p>
     * FileChannel不能转换成非阻塞IO
     */
    @Test
    public void serverBlocking() throws IOException {
        //1. 获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        FileChannel fileChannel = FileChannel.open(Paths.get("E:/filetest/哪吒2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //2. 绑定连接
        serverSocketChannel.bind(new InetSocketAddress(51399));

        //3. 获取客户端连接的通道
        SocketChannel socketChannel = serverSocketChannel.accept();

        //4. 分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //5. 接收客户端的数据，并保存到本地
        while (socketChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        //发送反馈给客户端
        byteBuffer.put("服务端接收数据成功".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);

        close(fileChannel, serverSocketChannel, socketChannel);
    }

    @Test
    public void clientBlocking() throws IOException {
        //客户端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 51399));
        FileChannel fileChannel = FileChannel.open(Paths.get("E:/filetest/哪吒.jpg"), StandardOpenOption.READ);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (fileChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        socketChannel.shutdownOutput();
        //接收服务端的反馈
        int len = 0;
        while((len = socketChannel.read(byteBuffer)) != -1){
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, len));
            byteBuffer.clear();
        }

        close(fileChannel, socketChannel);

    }

    void close(Closeable... closeables) throws IOException {
        for (Closeable closeable : closeables) {
            closeable.close();
        }
    }

}
