package com.nijunyang.hadoop.hdfs.collect;

import org.junit.Test;

import java.util.Timer;

/**
 * Description: 
 * Created by nijunyang on 2019/12/26 10:27
 */
public class DataCollect {

    @Test
    public void test1() throws Exception{
        Timer timer = new Timer();

        timer.schedule(new CollectTask(), 0, 60*60*1000L);

        timer.schedule(new BackupCleanTask(), 0, 60*60*1000L);
    }
}
