package com.nijunyang.mybatis.mapper;

import com.nijunyang.mybatis.model.User;
import com.nijunyang.mybatis.page.UserPageQuery;

import java.util.List;

/**
 * @author: create by nijunyang
 * @date:2019/7/3
 */
public interface UserMapper {
    int insertUser(User user);
    User selectByCard(String card);

    int getCount(UserPageQuery query);
    List<User> getPageList(UserPageQuery query);
}
