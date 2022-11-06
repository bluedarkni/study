package com.nijunyang.autoconfig.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  * 自定义的配置类，映射配置文件参数
 * @author: create by nijunyang
 * @date:2019/7/15
 */
@ConfigurationProperties(prefix = "spring.userdef.model")
public class UserDefProperties {
    private String userName;
    private Integer age;
    private Double height;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
