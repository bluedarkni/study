package com.nijunyang.hadoop.hdfs.collect;

import java.io.File;
import java.util.TimerTask;

/**
 * Description: 
 * Created by nijunyang on 2019/12/26 10:28
 */
public class CollectTask extends TimerTask {
    @Override public void run() {
        File srcDir = new File("");
        File[] files = srcDir.listFiles(((dir, name) -> {
            if (name.startsWith("")) {
                return true;
            }
            return false;
        }));
    }
}
