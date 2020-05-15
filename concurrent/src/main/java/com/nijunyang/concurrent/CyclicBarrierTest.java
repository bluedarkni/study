package com.nijunyang.concurrent;

import java.util.concurrent.CyclicBarrier;

/**
 * @author: create by nijunyang
 * @date:2019/9/5
 */
public class CyclicBarrierTest implements Runnable{
    private CyclicBarrier cyclicBarrier;
    private int index;
    public CyclicBarrierTest(CyclicBarrier cyclicBarrier, int index) {
        this.cyclicBarrier = cyclicBarrier;
        this.index = index;
    }
    public void run() {
        try {
//            System.out.println("index: " + index);
//            index--;
            System.out.println(Thread.currentThread().getName() + "进度条100%... ");
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(11, new Runnable() {
                    public void run() {
                        System.out.println("所有人进度条100%，准备开始游戏");
                    }
                });
        for (int i = 0; i < 10; i++) {
            new Thread(new CyclicBarrierTest(cyclicBarrier,i), "线程" + i).start();
        }
        cyclicBarrier.await();
        Thread.sleep(300);
        System.out.println("开始游戏....");
    }

}
