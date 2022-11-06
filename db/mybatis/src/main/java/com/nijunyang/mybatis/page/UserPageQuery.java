package com.nijunyang.mybatis.page;

/**
 * @author nijunyang
 * @since 2021/8/24
 */

public class UserPageQuery extends PageRequest{

    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
