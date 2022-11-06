package com.nijunyang.autoconfig.config;

import com.nijunyang.autoconfig.model.User;
import com.nijunyang.autoconfig.properties.UserDefProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 自定义启动类配置类
 * @author: create by nijunyang
 * @date:2019/7/15
 */
@Slf4j
@EnableConfigurationProperties(value = UserDefProperties.class)
public class UserDefAutoConfiguration {

    @Autowired
    private UserDefProperties userDefProperties;

    @Bean
    public User user(){
        log.info("自定义自动装配UserDefAutoConfiguration.....");
        User user = new User();
        user.setAge(userDefProperties.getAge());
        user.setUserName(userDefProperties.getUserName());
        user.setHeight(userDefProperties.getHeight());
        return user;
    }
}
