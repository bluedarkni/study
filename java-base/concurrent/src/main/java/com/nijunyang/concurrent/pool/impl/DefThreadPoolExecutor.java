package com.nijunyang.concurrent.pool.impl;

import com.nijunyang.concurrent.pool.DefExecutorService;
import com.nijunyang.concurrent.pool.policy.DefaultPolicyHandler;
import com.nijunyang.concurrent.pool.policy.PolicyHandler;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: create by nijunyang
 * @date:2019/8/10
 */
public class DefThreadPoolExecutor implements DefExecutorService {

    /**
     * 默认队列大小
     */
    private static final int defaultQueueSize = 5;

    /**
     * 默认池的大小
     */
    private static final int defaultPoolSize = 5;

    /**
     * 线程池最大的大小
     */
    private static final int maxPoolSize = 50;

    private static final long defaultAliveTime = 60l;

    /**
     * 线程池大小
     */
    private volatile int poolsize;

    /**
     * 拒绝策略
     */
    private volatile PolicyHandler policyHandler;

    /**
     * 是否已经中断
     */
    private volatile boolean isShutDown = false;

    /**
     * active当前激活线程数
     */
    private AtomicInteger ctl = new AtomicInteger();

    /**
     * 队列
     */
    private final BlockingQueue<Runnable> workQueue;

    /**
     * Lock
     */
    private final ReentrantLock mainLock = new ReentrantLock();

    /**
     * 是否允许超时
     */
    private volatile boolean allowThreadTimeOut;

    private volatile long keepAliveTime;

    /**
     * worker集合
     */
    private final HashSet<Worker> workers = new HashSet<Worker>();

    public DefThreadPoolExecutor()
    {
        this(defaultPoolSize,defaultQueueSize,defaultAliveTime,new DefaultPolicyHandler());
    }

    public DefThreadPoolExecutor(int poolsize,int queueSize,long keepAliveTime,PolicyHandler policyHandler){
        if(poolsize <= 0 || poolsize > maxPoolSize ){
            throw new IllegalArgumentException("线程池大小不能<=0");
        }
        this.poolsize = poolsize;
        this.policyHandler = policyHandler;
        this.keepAliveTime = keepAliveTime;
        if(keepAliveTime > 0){
            allowThreadTimeOut = true;
        }
        this.workQueue = new ArrayBlockingQueue<>(queueSize);
    }


    @Override
    public void execute(Runnable task) {
        if(task == null) {
            throw new NullPointerException();
        }
        int workerCount = ctl.get();
        //当前任务数小于池子大小添加任务，否则添加到队列中，队列若满，则进入拒绝策略
        if(workerCount < poolsize){
            if(addWorker(task, true)){
                return;
            }
        }else if(workQueue.offer(task)){
            System.out.println("池子已满，加入队列");
        }else{
            policyHandler.rejectedExecution(task, this);
        }
    }

    private boolean addWorker(Runnable r,boolean core){
        if(core){
            //计数器+1
            ctl.incrementAndGet();
        }
        boolean workerAdded = false;
        boolean workerStarted = false;

        Worker w = new Worker(r);
        Thread t = w.thread;
        if(t != null){
            ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                if(!isShutDown){ // 线程池未关闭
                    if (t.isAlive()) // 检查线程是否已经处于运行状态，start方法不能重复调用执行
                        throw new IllegalThreadStateException();
                    workers.add(w);
                    workerAdded = true;
                }
            } finally {
                mainLock.unlock();
            }
            if (workerAdded){
                t.start();
                workerStarted = true;
            }
        }
        return workerStarted;
    }

    @Override
    public Runnable getTask() {
        return null;
    }

    static AtomicInteger atomic = new AtomicInteger();
    private class Worker implements Runnable{

        final Thread thread;
        /** Initial task to run.  Possibly null. */
        Runnable firstTask;

        public Worker(Runnable r){
            this.firstTask = r;
            this.thread = new Thread(this,"thread-ame-"+ atomic.incrementAndGet());
        }

        public void run() {
//            runWorker(this);
        }
    }
}
