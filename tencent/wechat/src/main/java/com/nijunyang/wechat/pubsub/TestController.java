package com.nijunyang.wechat.pubsub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Created by nijunyang on 2023/3/25 23:26
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping()
    public Object scheduleCache() {
        return 1;
    }
}
