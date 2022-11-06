package com.nijunyang.mybatis.page;

import com.nijunyang.mybatis.mapper.UserMapper;
import com.nijunyang.mybatis.model.User;

import java.util.List;

/**
 * @author nijunyang
 * @since 2021/8/24
 */
public class PageServer {

    private UserMapper userMapper;

    public Pagination<User> selectPageList(UserPageQuery query) {
        Pagination<User> page = new Pagination<>();
        page.setPage(query.getPage());
        page.setPageSize(query.getPageSize());
        int count = userMapper.getCount(query);
        page.setTotal(count);
        if (count > 0) {
            List<User> list = userMapper.getPageList(query);
            page.setResult(list);
        }
        return page;
    }
}
