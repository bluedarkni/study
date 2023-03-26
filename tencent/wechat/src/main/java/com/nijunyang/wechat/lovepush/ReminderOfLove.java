package com.nijunyang.wechat.lovepush;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description:
 * Created by nijunyang on 2023/3/25 21:53
 */
@Slf4j
@Component
public class ReminderOfLove {


    @Scheduled(cron = "0 0 7 * * ?")
//    @Scheduled(cron = "0 40 23 * * ?")
    public void sendLove() {
        log.info("------推送信息开始------");
        WechatApp.main00();
        log.info("------推送信息完成------");
    }
}
