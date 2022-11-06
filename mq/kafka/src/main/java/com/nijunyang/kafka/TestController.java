package com.nijunyang.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Created by nijunyang on 2019/12/6 20:50
 */
@RestController
public class TestController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/send")
    public String send() {
        kafkaTemplate.send("mytopic", "key", "this is a msg");
        return  "this is a msg";
    }

    @RequestMapping("/test")
    public String  test() {
        return  "this is a msg";
    }
}
