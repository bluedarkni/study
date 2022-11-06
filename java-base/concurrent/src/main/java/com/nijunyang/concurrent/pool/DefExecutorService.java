package com.nijunyang.concurrent.pool;

/**
 * @author: create by nijunyang
 * @date:2019/8/10
 */
public interface DefExecutorService {

    /**
     * 任务执行
     * @param task
     */
    void execute(Runnable task);

    /**
     * 获取任务
     */
    Runnable getTask();
}
