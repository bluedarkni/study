package com.nijunyang;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: create by nijunyang
 * @date:2019/9/10
 */
public class TestHeap {
    byte[] bytes = new byte[1024*100];
    public static void main(String[] args) throws InterruptedException {
        List<TestHeap> list = new ArrayList<>();
        while (true){
            list.add(new TestHeap());
            Thread.sleep(30);
        }
    }
}
