package com.nijunyang.concurrent.pool;

import java.time.Instant;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Created by nijunyang on 2020/4/3 22:08
 */
public class Test {

    ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);


    public void testScheduledOnce() {
        long start = Instant.now().toEpochMilli();
        System.out.println(start);
        System.out.println(System.currentTimeMillis());

        scheduledThreadPoolExecutor.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        long now = Instant.now().toEpochMilli();
                        System.out.println(now - start);
                    }
                },
                10,
                TimeUnit.SECONDS);
        System.out.println("-----");

    }

    public void testScheduledOnce(int i) {

        scheduledThreadPoolExecutor.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(i);
                    }
                },
                5,
                TimeUnit.SECONDS);
    }

    public void testScheduledCycle() {
        long start = Instant.now().toEpochMilli();
        System.out.println(start);
        System.out.println(System.currentTimeMillis());

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        long now = Instant.now().toEpochMilli();
                        System.out.println(now - start);
                    }
                },
                5,
                2,
                TimeUnit.SECONDS);
        System.out.println("-----");

    }

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(10);
        Task task = new Task();
        RunnableScheduledFuture<?> schedule =
                (RunnableScheduledFuture<?>) scheduledThreadPoolExecutor.schedule(task, 10, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.remove(schedule);
        System.out.println(Instant.now().getEpochSecond());


    }

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(Instant.now().getEpochSecond());
        }
    }
}
