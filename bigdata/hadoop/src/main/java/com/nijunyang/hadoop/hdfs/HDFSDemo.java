package com.nijunyang.hadoop.hdfs;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.util.Arrays;

/**
 * Description:
 * Created by nijunyang on 2019/12/25 20:26
 */
public class HDFSDemo {

    FileSystem fs;

    @Before
    public void init() throws Exception{

        URI uri = new URI("hdfs://nijunyang68:9000/");
        /**
         * Configuration 构造会从 classpath中加载core-default.xml hdfs-default.xml core-site.xml hdfs-site.xml等文件
         * 也可使用set方法进行自己设置值
         * https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-hdfs/hdfs-default.xml
         */
        Configuration conf = new Configuration();
        conf.set("dfs.replication", "2");
        // 切块的规格大小：32M
        conf.set("dfs.blocksize", "32m");
        fs = FileSystem.get(uri, conf, "root");
    }

    @Test
    public void test1() throws Exception {
        // 上传一个文件到HDFS中
        fs.copyFromLocalFile(new Path("E:/安装包/linux/jdk-8u191-linux-x64.tar.gz"), new Path("/soft/"));
        //下载到本地
        fs.copyToLocalFile(new Path("/soft/jdk-8u191-linux-x64.tar.gz"), new Path("f:/"));
        //在hdfs内部移动文件/修改名称
        fs.rename(new Path("/redis-5.0.5.tar.gz"), new Path("/redis5.0.5.tar.gz"));
        //在hdfs中创建文件夹
        fs.mkdirs(new Path("/xx/yy/zz"));
        //在hdfs中删除文件或文件夹
        fs.delete(new Path("/xx/yy/zz"), true);
        //查询hdfs指定目录下的文件信息
        RemoteIterator<LocatedFileStatus> iter = fs.listFiles(new Path("/"), true);
        while(iter.hasNext()){
            LocatedFileStatus status = iter.next();
            System.out.println("文件全路径："+status.getPath());
            System.out.println("块大小："+status.getBlockSize());
            System.out.println("文件长度："+status.getLen());
            System.out.println("副本数量："+status.getReplication());
            System.out.println("块信息："+ Arrays.toString(status.getBlockLocations()));
            System.out.println("--------------------------------");
        }
        //查询hdfs指定目录下的文件和文件夹信息
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for(FileStatus status:listStatus){
            System.out.println("文件全路径："+status.getPath());
            System.out.println(status.isDirectory()?"这是文件夹":"这是文件");
            System.out.println("块大小："+status.getBlockSize());
            System.out.println("文件长度："+status.getLen());
            System.out.println("副本数量："+status.getReplication());
            System.out.println("--------------------------------");
        }
        fs.close();
    }
}
