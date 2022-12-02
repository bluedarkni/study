package com.nijunyang.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * Description:
 * Created by nijunyang on 2022/11/14 14:27
 */
public class IOUtil {
    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
                System.out.println("关闭连接");
            } catch (IOException e) {
                System.out.println("关闭连接异常");
            }
        }
    }
}
