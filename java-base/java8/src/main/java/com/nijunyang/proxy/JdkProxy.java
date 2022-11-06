package com.nijunyang.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author nijunyang
 * @since 2021/8/25
 */
public class JdkProxy<T> {

    //维护一个目标对象
    private T target;

    public JdkProxy(T target) {
        this.target = target;
    }

    //给目标对象生成代理对象
    public T getProxyInstance() {
        return (T)Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("代理方法执行");
                        //执行目标对象方法
                        return method.invoke(proxy, args);
                    }
                });
    }

    public static void main(String[] args) {
        JdkProxy<Animal> jdkProxy = new JdkProxy<>(new Cat());
        Animal proxyInstance = jdkProxy.getProxyInstance();
        proxyInstance.eat();
    }



    private interface Animal {
        void eat();
    }

    private static class Cat implements Animal{
        @Override
        public void eat() {
            System.out.println("吃鱼");
        }
    }
}
