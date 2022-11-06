package com.nijunyang.kafka.controller;

import com.nijunyang.kafka.model.User;
import com.nijunyang.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Created by nijunyang on 2019/12/7 22:24
 */
@RestController
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/send/{name}/{age}")
    public void send(@PathVariable String name, @PathVariable int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        kafkaTemplate.send("test-topic", 0, "key", JsonUtils.write2JsonString(user));
    }
}
