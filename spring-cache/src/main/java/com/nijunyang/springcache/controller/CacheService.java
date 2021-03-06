package com.nijunyang.springcache.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Created by nijunyang on 2019/12/12 14:43
 */
@Service
public class CacheService implements BeanFactoryAware {

    private BeanFactory beanFactory;

    /**
     * 默认是JDK缓存
     * value 缓存组件的名字
     * key   key缓存key（spel表达式）
     * unless 符合表达式的结果不缓存
     * condition 条件  满足走缓存，不满足直接执行方法体
     * sync    同步缓存（多线程情况锁住缓存计算，只允许一个线程操作），个人理解就类似在方法上加锁，避免多线程场景，同样的参数请求多个过来，每个请求都去执行方法体进行计算
     */
    /**
     *数据存放在 org.springframework.cache.concurrent.ConcurrentMapCache中
     * org.springframework.cache.interceptor.CacheAspectSupport#getCaches 获取缓存数据
     * org.springframework.cache.concurrent.ConcurrentMapCacheManager#createConcurrentMapCache(java.lang.String) 初始化缓存Map
     */
//    @Cacheable(value = "cache1", key = "#name", unless = "null", condition = "#name.length() > 2", sync = false)
    public String cache(String name) {
        CacheService cacheService = beanFactory.getBean(this.getClass());
        String s = cacheService.cache1(name);
        return s;
    }

    @Cacheable(value = "cache1", key = "#name", condition = "#name.length() > 2")
    public String cache1(String name) {
        String cache = "_cache";
        return name + cache;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
