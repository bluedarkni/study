package com.nijunyang.codegen.util;

import java.io.File;

/**
 * Description: 
 * Created by nijunyang on 2019/12/9 10:01
 */
public final class PathUtils {

    public static String combinePath(String...args){
        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("path is empty.");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i == args.length - 1) {
                break;
            }
            sb.append(File.separator);
        }
        return sb.toString();
    }
}
