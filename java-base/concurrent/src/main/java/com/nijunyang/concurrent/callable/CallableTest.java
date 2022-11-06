package com.nijunyang.concurrent.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: create by nijunyang
 * @date:2019/8/4
 */
public class CallableTest  {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableDef callableDef = new CallableDef();
        FutureTask task = new FutureTask(callableDef);
        new Thread(task).start();
        System.out.println(task.get());
    }

}

class CallableDef implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        return 123;
    }
}
