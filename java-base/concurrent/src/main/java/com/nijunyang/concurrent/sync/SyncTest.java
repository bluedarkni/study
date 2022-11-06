package com.nijunyang.concurrent.sync;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:
 * Created by nijunyang on 2020/5/7 23:03
 */
public class SyncTest {

    private Lock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        SyncTest syncTest = new SyncTest();
        new Thread(()->syncTest.testLock(), "线程1").start();
        new Thread(()->syncTest.testLock(), "线程2").start();
        new Thread(()->syncTest.testLock(), "线程3").start();
    }

    public void testLock() {
        lock.lock();
        System.out.println("线程:--->" + Thread.currentThread().getName() + "第一次加锁");
        lock.lock();
        System.out.println("线程:--->" + Thread.currentThread().getName() + "第二次加锁");
        lock.unlock();
        System.out.println("线程:--->" + Thread.currentThread().getName() + "释放第一个锁");
        lock.unlock();
        System.out.println("线程:--->" + Thread.currentThread().getName() + "释放第一个锁");
    }


//    public void testUnsafe() throws Exception {
//        //手动加对象锁
//        Field field = Unsafe.class.getDeclaredField("theUnsafe");
//        field.setAccessible(true);
//        Unsafe unsafe = (Unsafe) field.get(null);
//        unsafe.monitorEnter(objectLock);
//        unsafe.monitorExit(objectLock);
//    }
}
