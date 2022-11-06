package com.nijunyang.hadoop.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Description: 
 * Created by nijunyang on 2019/12/30 13:29
 */
public class Test {
    public static void main(String[] args) throws IOException {
        File file = new File("hadoop/src/main/resources/words.txt");
        if (!file.exists()) {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            for (int i = 0; i < 10000; i++) {
                fileWriter.append("Think twice before acting");
                fileWriter.append(System.lineSeparator());
                fileWriter.append("The Apache Hadoop software library is a framework that allows for the distributed processing of large data sets across clusters of computers using simple programming models");
                fileWriter.append(System.lineSeparator());
            }
            fileWriter.flush();
            fileWriter.close();
        }
    }
}
