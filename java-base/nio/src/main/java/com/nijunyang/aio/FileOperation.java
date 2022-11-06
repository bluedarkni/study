package com.nijunyang.aio;

import cn.hutool.core.io.IoUtil;
import com.nijunyang.util.PathUtils;

import java.io.*;

/**
 * Description:
 * Created by nijunyang on 2020/8/14 22:34
 */
public class FileOperation {

    public static void main(String[] args) {
        read();
    }

    public static void read () {
//        String path = PathUtils.combinePath("src", "main", "resources");

        try(/*FileInputStream fis = new FileInputStream(path)*/
                InputStream fis = new ByteArrayInputStream("中国话".getBytes("utf-8"));
                InputStreamReader reader = new InputStreamReader(fis, "utf-8")) {
//            String read = IoUtil.read(fis, "utf-8");
            byte[] tempBytes = new byte[4];
            char[] chars = new char[2];
            int index;
            StringBuilder sb = new StringBuilder();
//            while ((index = fis.read(tempBytes)) != -1) {
//                sb.append(new String(tempBytes, 0, index));
//            }
            System.out.println(sb);
            sb = new StringBuilder();
            while ((index = reader.read(chars)) != -1) {
                sb.append(new String(chars, 0, index));
            }
            System.out.println(sb);



        } catch (IOException e) {

        }

    }
}
