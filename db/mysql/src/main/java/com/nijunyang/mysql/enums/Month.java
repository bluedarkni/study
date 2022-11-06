package com.nijunyang.mysql.enums;

/**
 * Description:
 * Created by nijunyang on 2020/2/20 16:39
 */
public enum Month implements EnumEntity {

    JANUARY("january"),

    FEBRUARY("february"),

    MARCH("march"),

    APRIL("april"),

    MAY("may"),

    JUNE("june"),

    JULY("july"),

    AUGUST("august"),

    SEPTEMBER("september"),

    OCTOBER("october"),

    NOVEMBER("november"),

    DECEMBER("december");
    ;

    private String name;

    Month(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
