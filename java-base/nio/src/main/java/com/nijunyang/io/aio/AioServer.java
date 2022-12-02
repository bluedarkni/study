package com.nijunyang.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Description:
 * Created by nijunyang on 2022/11/14 14:29
 */
public class AioServer {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        AsynchronousServerSocketChannel asynchronousServerSocketChannel =  AsynchronousServerSocketChannel.open();
        asynchronousServerSocketChannel.bind(new InetSocketAddress(11113));
        Future<AsynchronousSocketChannel> acceptFuture = asynchronousServerSocketChannel.accept();
        AsynchronousSocketChannel asynchronousSocketChannel = acceptFuture.get();

    }


    private static class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

        @Override
        public void completed(AsynchronousSocketChannel result, AioServer attachment) {
            System.out.println("连接成功");
        }

        @Override
        public void failed(Throwable exc, AioServer attachment) {
            System.out.println("连接失败");

        }
    }
}
