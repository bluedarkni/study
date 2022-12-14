package com.nijunyang.eureka.extension.component.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.nijunyang.eureka.extension.constants.Constant;
import com.nijunyang.eureka.extension.util.AopTargetUtils;
import feign.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.FeignLoadBalancer;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by nijunyang on 2020/11/18 14:38
 *
 * @author nijunyang
 */
@Slf4j
@RefreshScope
public class MessageHandler {

    private static final long DEFAULT_DELAY = 500L;

    @Autowired(required = false)
    EurekaClient eurekaClient;

    @Autowired(required = false)
    Client feignClient;

    @Autowired
    ObjectMapper objectMapper;

    public void handleMessage(Object object)
            throws Exception {
        log.info("subscribe message: {}", object.toString());
        String s = object.toString();
        String substring = s.substring(s.indexOf(",") + 1, s.length() - 1);
        MessageHolder messageHolder = objectMapper.readValue(substring, MessageHolder.class);
        long cancelTime = messageHolder.getSendTime();
        long now = System.currentTimeMillis();
//        if (now <= cancelTime + DEFAULT_DELAY + evictionIntervalTimerInMs) {
//            Thread.sleep(evictionIntervalTimerInMs + DEFAULT_DELAY- (now - cancelTime));
//        }
//        Thread.sleep(DEFAULT_DELAY);

        log.info("??????????????? " + substring);
        refreshEurekaAndRibbonCache(messageHolder, 0);

    }

    /**
     * ??????eureka???ribbon??????????????????????????????
     *
     * @param messageHolder messageHolder
     * @param cnt           ????????????
     * @throws Exception Exception
     */
    private void refreshEurekaAndRibbonCache(MessageHolder messageHolder, int cnt) throws Exception {
        if (cnt >= Constant.RETRY_TIME) {
            log.error("??????????????????????????????...");
            return;
        }
        //1.??????????????????????????????????????????DiscoveryClient.fetchRegistry()
        Object eurekaClientNative = AopTargetUtils.getTarget(eurekaClient);
        if (eurekaClientNative instanceof DiscoveryClient) {
            DiscoveryClient discoveryClient = (DiscoveryClient) eurekaClientNative;
            Method fetchRegistryMethod = DiscoveryClient.class.getDeclaredMethod("fetchRegistry", boolean.class);
            fetchRegistryMethod.setAccessible(true);
            Object result = fetchRegistryMethod.invoke(discoveryClient, false);
            log.info("eureka client local cache refreshed...");
            if (Boolean.TRUE.equals(result)) {
                // 2.??????ribbon????????????
                refreshRibbonCache(discoveryClient, messageHolder, cnt + 1);
            } else {
                Thread.sleep(DEFAULT_DELAY + DEFAULT_DELAY);
                refreshEurekaAndRibbonCache(messageHolder, cnt + 1);
            }
        }
    }

    /**
     * ??????ribbon????????????
     *
     * @param discoveryClient discoveryClient
     * @param messageHolder   messageHolder
     * @param cnt             cnt
     * @throws Exception Exception
     */
    private void refreshRibbonCache(
            DiscoveryClient discoveryClient, MessageHolder messageHolder, int cnt) throws Exception {
        log.info("start refresh ribbon cache...");
        Object feignClientNative = AopTargetUtils.getTarget(feignClient);
        String appName = messageHolder.getAppName();
        Application application = discoveryClient.getApplication(appName);
        int instancesSize = application == null ? 0 : application.getInstances().size();
        if (feignClientNative instanceof LoadBalancerFeignClient) {
            LoadBalancerFeignClient loadBalancerFeignClient = (LoadBalancerFeignClient) feignClient;
            //??????CachingSpringLoadBalancerFactory lbClientFactory;
            Field lbClientFactoryField = LoadBalancerFeignClient.class.getDeclaredField("lbClientFactory");
            lbClientFactoryField.setAccessible(true);
            CachingSpringLoadBalancerFactory balancerFactory =
                    (CachingSpringLoadBalancerFactory) lbClientFactoryField.get(loadBalancerFeignClient);
            //??????Map<String, FeignLoadBalancer> cache = new ConcurrentReferenceHashMap<>()??????
            Field cacheField = balancerFactory.getClass().getDeclaredField("cache");
            cacheField.setAccessible(true);
            Map<String, FeignLoadBalancer> feignLoadBalancerMap = (Map) cacheField.get(balancerFactory);
            //????????????????????????????????????
            FeignLoadBalancer feignLoadBalancer = feignLoadBalancerMap.get(appName);
            if (feignLoadBalancer != null) {
                ILoadBalancer loadBalancer = feignLoadBalancer.getLoadBalancer();
                if (loadBalancer instanceof DynamicServerListLoadBalancer) {
                    int oldSize = loadBalancer.getAllServers().size();
                    ((DynamicServerListLoadBalancer) loadBalancer).updateListOfServers();
                    int newSize = loadBalancer.getAllServers().size();
                    //eureka????????????????????????????????????
                    if (instancesSize == newSize && newSize != oldSize) {
                        log.info("{} local server list refreshed, number??? {} -> {}, shutdown appId: {}",
                                appName, oldSize, newSize, messageHolder.getAppId());
                    } else {
                        log.info("eureka????????????????????????:{}, ribbon??????????????????????????????{} - {}",
                                instancesSize, oldSize, newSize);
                        Thread.sleep(DEFAULT_DELAY);
                        refreshEurekaAndRibbonCache(messageHolder, cnt + 1);
                    }
                }
            } else {
                log.info("{} ??????????????????????????????feignLoadBalancer", appName);
            }
        }
    }
}
