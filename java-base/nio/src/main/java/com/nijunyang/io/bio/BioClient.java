package com.nijunyang.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Description:
 * Created by nijunyang on 2022/11/14 14:30
 */
public class BioClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        while (true) {

            Socket socket = new Socket("127.0.0.1", 11111);

            //读取服务器端数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //向服务器端发送数据
            PrintStream writer = new PrintStream(socket.getOutputStream());
            System.out.print("请输入: \n");
            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
            writer.println(str);

            String ret = reader.readLine();
            System.out.println("服务器端返回过来的是: " + ret);
            // 如接收到 "OK" 则断开连接
            if ("OK".equals(ret)) {
                System.out.println("客户端将关闭连接");
                Thread.sleep(500);
                break;
            }

            reader.close();
//            writer.close();
            socket.close();


        }
    }
}
