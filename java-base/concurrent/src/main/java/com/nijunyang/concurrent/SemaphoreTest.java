package com.nijunyang.concurrent;

import java.util.concurrent.Semaphore;

/**
 * Description:
 * Created by nijunyang on 2020/5/13 23:31
 */
public class SemaphoreTest {
    Semaphore semaphore;

    public SemaphoreTest(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public static void main(String[] args) {
        SemaphoreTest semaphoreTest = new SemaphoreTest(new Semaphore(2, true));
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    semaphoreTest.test();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "线程" + i);
            thread.start();
        }
        System.out.println("线程创建完毕.");
    }

    public void test() throws InterruptedException {
        semaphore.acquire();
        System.out.println(Thread.currentThread().getName() + "执行.");
        Thread.sleep(4000);
        semaphore.release();
    }
}
