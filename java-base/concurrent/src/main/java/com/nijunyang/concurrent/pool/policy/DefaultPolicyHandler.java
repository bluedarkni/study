package com.nijunyang.concurrent.pool.policy;

import com.nijunyang.concurrent.pool.DefExecutorService;

/**
 * @author: create by nijunyang
 * @date:2019/8/10
 */
public class DefaultPolicyHandler implements PolicyHandler {

    @Override
    public void rejectedExecution(Runnable runnable, DefExecutorService executor) {
        throw new RuntimeException("任务已满");
    }
}
