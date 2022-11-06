package com.nijunyang.mysql.myclass;

import com.nijunyang.mysql.enums.EnumEntity;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 * Created by nijunyang on 2020/2/20 14:44
 */
public class MyList<E extends EnumEntity> extends ArrayList<E> {
    private static final long serialVersionUID = 2749722712589356687L;

    private static ConcurrentHashMap<String, EnumEntity> enumMap = new ConcurrentHashMap<>(8);

    public String getString() {
        StringBuffer sb = new StringBuffer();
        for (EnumEntity enumEntity : this) {
            enumMap.put(enumEntity.getName(), enumEntity);
            sb.append(enumEntity.getName());
            sb.append(",");
        }
        return sb.toString();
    }

    public EnumEntity getEnumByName(String name) {
        return enumMap.get(name);
    }
}
