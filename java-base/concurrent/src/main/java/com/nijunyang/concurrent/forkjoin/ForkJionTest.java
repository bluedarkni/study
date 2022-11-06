package com.nijunyang.concurrent.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author: create by nijunyang
 * @date:2019/8/4
 */
public class ForkJionTest {
    public static void main(String[] args) {
        Instant start = Instant.now();
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<Long> calcTask = new ForkJionSunCalc(0L, 100000L);
        long sum = pool.invoke(calcTask);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println(end.toString());
        System.out.println(LocalDateTime.now().toString());
        System.out.println(Duration.between(start,end).toMillis());

        long result = 0;
        for (long i = 0; i <= 100000L; i++) {
            result += i;
        }
        System.out.println(result);

    }
}

class ForkJionSunCalc extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static final long threshold = 20000L;

    public ForkJionSunCalc(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= threshold) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long middle = (start + end) / 2;
            ForkJionSunCalc calcLeft = new ForkJionSunCalc(start, middle);
            calcLeft.fork();
            ForkJionSunCalc calcRight = new ForkJionSunCalc(middle + 1, end);
            calcRight.fork();
            return calcLeft.join() + calcRight.join();

        }
    }
}
