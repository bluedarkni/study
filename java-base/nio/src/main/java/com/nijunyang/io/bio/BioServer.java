package com.nijunyang.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Description:
 * Created by nijunyang on 2022/11/14 14:32
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(11111);
        while (true) {
            System.out.println("11111");
            Socket accept = serverSocket.accept();
            System.out.println("22222");
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                    String s = reader.readLine();
                    System.out.println(s);
                    if ("123".equals(s)) {
                        Thread.sleep(60000);
                    }
                    PrintStream writer = new PrintStream(accept.getOutputStream());
                    writer.print(s + "_" + 321);
                    reader.close();
                    writer.close();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        accept.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
