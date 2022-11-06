package com.nijunyang.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * Description:
 * Created by nijunyang on 2020/5/16 13:53
 */
public class CountDownLatchTest{

    private CountDownLatch countDownLatch;

    public CountDownLatchTest(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);
        CountDownLatchTest countDownLatchTest = new CountDownLatchTest(countDownLatch);

        new Thread(()-> {
            try {
                countDownLatchTest.method1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程1").start();
        new Thread(()-> {
            try {
                countDownLatchTest.method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程2").start();

        System.out.println("等待食材准备完毕...");
        countDownLatch.await();
        System.out.println("炒肉...");


//        System.out.println("------第二次使用-----");
//        new Thread(()-> {
//            try {
//                countDownLatchTest.method1();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"线程1").start();
//        new Thread(()-> {
//            try {
//                countDownLatchTest.method2();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"线程2").start();
//
//        System.out.println("等待食材准备完毕...");
//        countDownLatch.await();
//        System.out.println("炒肉...");

    }

    private void method1() throws InterruptedException {
        Thread.sleep(5000L);
        System.out.println("切菜完毕...");
        countDownLatch.countDown();
    }

    private void method2() throws InterruptedException {
        Thread.sleep(10000L);
        System.out.println("切肉完毕...");
        countDownLatch.countDown();
    }
}
