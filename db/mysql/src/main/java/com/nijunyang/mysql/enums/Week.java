package com.nijunyang.mysql.enums;

/**
 * Description:
 * Created by nijunyang on 2020/2/20 14:40
 */
public enum Week implements EnumEntity{

    MONDAY("monday"),

    TUESDAY("tuesday"),

    WEDNESDAY("wednesday"),

    THURSDAY("thursday"),

    FRIDAY("friday"),

    SATURDAY("saturday"),

    SUNDAY("sunday"),
    ;

    private String name;

    Week(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
