package com.nijunyang.wechat.pubsub;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Description:
 * Created by nijunyang on 2023/3/25 21:54
 */
public class DateUtils {


    /**
     * 下一个生日计算
     * @param birthdayMonth
     * @param birthdayDay
     * @return
     */
    public static Long calcNextBirthday(Integer birthdayMonth, Integer birthdayDay) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime nextBirthday = LocalDateTime.of(today.getYear(), birthdayMonth, birthdayDay,0,0);
        if (nextBirthday.isBefore(today) || nextBirthday.isEqual(today)) {
            nextBirthday = nextBirthday.plusYears(1);
        }
        return LocalDateTimeUtil.between(today, nextBirthday, ChronoUnit.DAYS);
    }
}
