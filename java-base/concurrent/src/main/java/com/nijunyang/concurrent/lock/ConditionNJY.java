package com.nijunyang.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: create by nijunyang
 * @date:2019/6/1
 */
public class ConditionNJY {

    private static boolean flag;
    private static Lock lock = new ReentrantLock();
    private static Condition full = lock.newCondition();
    private static Condition empty = lock.newCondition();
    private static int i = 0;


    public static void main(String[] args)
    {
        Thread producer = new Thread("producer"){
            @Override
            public void run() {
                try {
                    ConditionNJY.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread consumer = new Thread("consumer"){
            @Override
            public void run() {
                try {
                    ConditionNJY.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        consumer.start();
        producer.start();


    }


    /**
     * 生产者
     * @throws InterruptedException
     */
    public static  void producer() throws InterruptedException {
        lock.lock();
        try {
            for (;;){
                if (flag || i >= 100) {
                    full.await();
                }

                for(;;){
                    i++;
                    if(i >= 100) {
                        System.out.println("i=" + i);
                        System.out.println("开始消费...");
                        Thread.sleep(1000);
                        //唤醒消费者 标记改为true
                        empty.signal();
                        flag = true;
                        break;
                    }
                }
            }

        }finally {
            lock.unlock();
        }
    }

    /**
     * 消费者
     * @throws InterruptedException
     */
    public static void consumer() throws InterruptedException {
        lock.lock();
        try {
            for (;;){
                if (!flag || i <= 0) {
                    empty.await();
                }

                for (;;){
                    i--;
                    if(i <= 0) {
                        System.out.println("i=" + i);
                        System.out.println("开始生产...");
                        Thread.sleep(1000);
                        flag = false;
                        full.signal();
                        break;
                    }
                }
            }

        }finally {
            lock.unlock();
        }
    }

}
