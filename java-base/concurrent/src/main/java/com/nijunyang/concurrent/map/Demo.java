package com.nijunyang.concurrent.map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nijunyang
 * @since 2021/8/24
 */
public class Demo {

    static final int MAXIMUM_CAPACITY = 1 << 30;

    public static void main(String[] args) {
        Map<Test, Integer> map = new HashMap<>(4);
        map.put(new Test("5454"), 1);
        map.put(new Test("5456"), 2);
        map.put(new Test("5458"), 3);
        map.put(new Test("5460"), 4);
        map.put(new Test("5462"), 5);
        map.put(new Test("5464"), 6);
        map.put(new Test("5466"), 7);
        map.put(new Test("5468"), 8);
        map.put(new Test("5470"), 9);
        map.put(new Test("5472"), 10);
        map.put(new Test("5474"), 10);

//        int n = 4;
//        System.out.println(12313 % n);
//        System.out.println((n - 1) & 12313);
//        System.out.println(12313 & (n - 1));
        System.out.println("5454".hashCode());
        System.out.println("5456".hashCode());
        System.out.println("5458".hashCode());
        System.out.println("5460".hashCode());
        System.out.println("5462".hashCode());
        System.out.println("5464".hashCode());
    }

    private static class Test {
        private String name;
        public Test(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return Integer.parseInt(name) % 2;
        }

        @Override
        public boolean equals(Object obj) {
            return this.name.equals(((Test)obj).name);
        }
    }
}
