package com.nijunyang;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author: create by nijunyang
 * @date:2019/9/7
 */
public class MyClassLoader extends ClassLoader{
    private String classPath;

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] data = loadByte(name);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            throw new ClassNotFoundException(name);
        }

    }

    private byte[] loadByte(String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();
        return data;
    }


    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("E:\\IdeaProject\\study\\java8\\src\\main\\java\\");
        Class clazz = classLoader.loadClass("com.nijunyang.model.A");
        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}
