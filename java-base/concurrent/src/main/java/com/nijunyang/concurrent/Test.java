package com.nijunyang.concurrent;

import sun.misc.Unsafe;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: create by nijunyang
 * @date:2019/7/28
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        Test1 test1 = new Test1();
        Thread thread1 = new Thread(test1,"thread1");
        Thread thread2 = new Thread(test1,"thread2");
        Thread thread3 = new Thread(test1,"thread3");
        thread2.start();
        thread1.start();
        thread3.start();
        Unsafe unsafe = Unsafe.getUnsafe();
//        unsafe.compareAndSwapInt()
        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.decrementAndGet();
        Executors.newCachedThreadPool();
    }

    static class Test1 implements Runnable{
        private volatile int sum = 100;

        @Override
        public void run() {
            synchronized (this){
                while (sum > 0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + sum--);
                }
            }
        }
    }
}




