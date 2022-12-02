package com.nijunyang.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Set;


/**
 * Description:
 * Created by nijunyang on 2022/11/14 14:33
 */
public class NioClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 11112));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_READ);
        ByteBuffer writeBuf = ByteBuffer.allocate(1024);
        ByteBuffer readBuf = ByteBuffer.allocate(1024);

        while (true) {
            System.out.print("请输入内容：");
            Scanner scan = new Scanner(System.in);
            String str = scan.next();
            writeBuf.put(str.getBytes());
            writeBuf.flip();
            socketChannel.write(writeBuf);
            writeBuf.clear();

            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            selectionKeys.forEach(key -> {
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    //读取数据
                    int len = 0;
                    try {
                        while((len = channel.read(readBuf)) > 0 ){
                            readBuf.flip();
                            String msg = new String(readBuf.array(), 0, len);
                            System.out.println(msg);
                            readBuf.clear();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                selectionKeys.remove(key);
            });
        }
    }
}
