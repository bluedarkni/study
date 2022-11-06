package com.nijunyang.mysql.config;


import com.nijunyang.mysql.enums.EnumEntity;
import com.nijunyang.mysql.myclass.MyList;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.CollectionUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description:
 * Created by nijunyang on 2020/2/20 14:53
 */
public class EnumListHandler<T extends MyList> extends BaseTypeHandler<MyList> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, MyList myList, JdbcType jdbcType) throws SQLException {
        String str = null;
        if (!CollectionUtils.isEmpty(myList)) {
            str = myList.getString();
        }
        if (jdbcType == null) {
            ps.setObject(i, str);
        } else {
            ps.setObject(i, str, jdbcType.TYPE_CODE);
        }
    }

    @Override
    public MyList getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        if (null == resultSet.getString(columnName) && resultSet.wasNull()) {
            return null;
        }
        String str = (String)resultSet.getObject(columnName);
        return getMyList(str);
    }

    @Override
    public MyList getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        if (null == resultSet.getString(columnIndex) && resultSet.wasNull()) {
            return null;
        }
        String str = (String)resultSet.getObject(columnIndex);
        return getMyList(str);
    }

    @Override
    public MyList getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        if (null == callableStatement.getString(columnIndex) && callableStatement.wasNull()) {
            return null;
        }
        String str = (String)callableStatement.getObject(columnIndex);
        return getMyList(str);
    }

    private MyList getMyList(String str) {
        if (str !=null && !str.isEmpty()) {
            MyList myList = new MyList();
            String[] split = str.split(",");
            for (String name : split) {
                EnumEntity enumEntity = myList.getEnumByName(name);
                myList.add(enumEntity);
            }
            return myList;
        }
        return null;
    }
}
