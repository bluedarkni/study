package com.nijunyang.concurrent;

import java.time.Instant;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 * Created by nijunyang on 2020/4/7 17:35
 */
public class ScheduledTest {

    private static final ScheduledThreadPoolExecutor scheduledExecutor = new ScheduledThreadPoolExecutor(10);

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Instant.now().getEpochSecond());
        RunnableScheduledFuture<?> schedule =
                (RunnableScheduledFuture<?>) scheduledExecutor.schedule(new Task(), 200, TimeUnit.SECONDS);
        scheduledExecutor.remove(schedule);

        System.out.println(Instant.now().getEpochSecond());
        scheduledExecutor.schedule(new Task(), 100, TimeUnit.SECONDS);

    }


    private static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(Instant.now().getEpochSecond());
        }
    }
}
