package com.nijunyang.concurrent.pool.policy;

import com.nijunyang.concurrent.pool.DefExecutorService;

/**
 * @author: create by nijunyang
 * @date:2019/8/10
 */
public interface PolicyHandler {
    void rejectedExecution(Runnable runnable, DefExecutorService executor);
}
