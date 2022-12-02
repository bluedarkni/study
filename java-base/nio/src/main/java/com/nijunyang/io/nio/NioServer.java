package com.nijunyang.io.nio;

import com.nijunyang.io.IOUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Description:
 * Created by nijunyang on 2022/11/14 14:35
 */
public class NioServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 11112));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(selectionKey -> {
                if (selectionKey.isAcceptable()) {
                    //接收就绪
                    try {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        System.out.println("连接就绪:" + socketChannel.hashCode());
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (selectionKey.isReadable()) {
                    //读就绪
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    System.out.println("通道使用:" + socketChannel.hashCode());
                    //读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    ByteBuffer writeBuf = ByteBuffer.allocate(1024);
                    int len = 0;
                    try {
                        while((len = socketChannel.read(buf)) > 0 ){
                            buf.flip();
                            String msg = new String(buf.array(), 0, len);
                            System.out.println(msg);
                            writeBuf.put((msg + "_响应").getBytes());
                            writeBuf.flip();
                            socketChannel.write(writeBuf);
                            writeBuf.clear();
                            buf.clear();
                        }
                    } catch (IOException e) {
                        selectionKeys.remove(selectionKey);
                        IOUtil.close(socketChannel);
                        return;
                    }
                } else if (selectionKey.isWritable()) {
                    //todo
                }
                selectionKeys.remove(selectionKey);
            });
        }
        IOUtil.close(selector);
    }
}
