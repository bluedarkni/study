package com.nijunyang.nio;

import org.junit.Test;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author: create by nijunyang
 * @date:2019/8/15
 */
public class ChannelTest {

    //直接缓冲区
    @Test
    public void test2() throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get("E:/安装包/CentOS-7-x86_64-DVD-1810.iso"), StandardOpenOption.READ);
//        FileChannel inChannel = FileChannel.open(Paths.get("E:/filetest/哪吒.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("E:/filetest/CentOS-7-x86_64-DVD-1810.iso"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
//        // 1 内存映射 过大文件会出错
//        MappedByteBuffer inMappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
//        MappedByteBuffer outMappedByteBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
//        //直接对缓冲区读写数据
//        byte[] bytes = new byte[inMappedByteBuffer.limit()];
//        inMappedByteBuffer.get(bytes);
//        outMappedByteBuffer.put(bytes);
        // 2 内存映射
        inChannel.transferTo(0, inChannel.size(), outChannel);
//        outChannel.transferFrom(inChannel, 0, inChannel.size());

        close(inChannel, outChannel);
    }

    @Test
    public void test1() throws Exception {
//        FileInputStream fis = new FileInputStream("E:/filetest/哪吒.png");
        FileInputStream fis = new FileInputStream("E:/filetest/test.txt");
//        FileOutputStream fos = new FileOutputStream("E:/filetest/哪吒.jpg");
        FileOutputStream fos = new FileOutputStream("E:/filetest/test1.txt");

        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //将通道中的数据存入缓冲区中
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int len = 0;
        byte[] bytes = new byte[1024];
        while((len = inChannel.read(buf)) != -1){
            buf.flip(); //切换读取数据的模式
            stringBuffer.append(new String(buf.array(), 0, len));
            buf.get(bytes,0, len);
            stringBuffer2.append(new String(bytes,0, len));
            //将缓冲区中的数据写入通道中
            outChannel.write(buf);
            buf.clear(); //清空缓冲区
        }
        close(fis, fos, inChannel, outChannel);
    }

    void close(Closeable ... closeables) throws IOException {
        for (Closeable closeable : closeables) {
            closeable.close();
        }
    }

}
