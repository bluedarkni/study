package com.nijunyang.nio;

import org.junit.Test;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

/**
 * @author: create by nijunyang
 * @date:2019/8/19
 */
public class NonBlockingNIOTest {
    @Test
    public void server() throws IOException {
        //1. 获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.切换成非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //3. 绑定连接
        serverSocketChannel.bind(new InetSocketAddress(51399));

        //获取选择器
        Selector selector = Selector.open();

        //将选择器注册到通道上,绑定监听事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //轮询获取事件
        while (selector.select() > 0){
            //获取当前选择器中所有注册的监听
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(x -> {
                //就绪事件
                if(x.isAcceptable()) {
                    //接收就绪
                    try {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(x.isReadable()){
                    //读就绪
                    SocketChannel socketChannel = (SocketChannel) x.channel();

                    //读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);

                    int len = 0;
                    try {
                        while((len = socketChannel.read(buf)) > 0 ){
                            buf.flip();
                            System.out.println(new String(buf.array(), 0, len));
                            buf.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                selectionKeys.remove(x);

            });
        }
        close(serverSocketChannel);
    }

    @Test
    public void client() throws IOException {
        //客户端
        //1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 51399));

        //2. 切换非阻塞模式
        socketChannel.configureBlocking(false);

        //3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4. 发送数据给服务端
        Scanner scan = new Scanner(System.in);

        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + "\n" + str).getBytes());
            buf.flip();
            socketChannel.write(buf);
            buf.clear();
        }

        close(socketChannel);

    }


    static void close(Closeable... closeables) throws IOException {
        for (Closeable closeable : closeables) {
            closeable.close();
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 51399));

        //2. 切换非阻塞模式
        socketChannel.configureBlocking(false);

        //3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //4. 发送数据给服务端
        Scanner scan = new Scanner(System.in);

        while(scan.hasNext()){
            String str = scan.next();
            buf.put((new Date().toString() + "\n" + str).getBytes());
            buf.flip();
            socketChannel.write(buf);
            buf.clear();
        }

        close(socketChannel);
    }
}
