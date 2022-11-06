package com.nijunyang.mongo.controller;

import com.nijunyang.mongo.model.UserMetadata;
import com.nijunyang.mongo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Created by nijunyang on 2020/4/30 14:04
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<String> add(@RequestBody UserMetadata userMetadata) {
        //{"gender":"MAN","idCardNo":"511129199312263324","nickname":"哪儿来哪儿去","phoneNumber":"13888833213","realName":"王屋太行"}
        String id = userService.add(userMetadata);
        return ResponseEntity.ok(id);
    }
    @GetMapping
    public ResponseEntity<UserMetadata> getUserMetadata(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String cardNo,
            @RequestParam(required = false) String phoneNo) {
        UserMetadata userMetadata = null;
        if (StringUtils.isNotBlank(id)) {
            userMetadata = userService.getUserMetadataById(id);
        }
        if (StringUtils.isNotBlank(cardNo)) {
            userMetadata = userService.getUserMetadataByCard(cardNo);
        }
        if (StringUtils.isNotBlank(phoneNo)) {
            userMetadata = userService.getUserMetadataByPhone(phoneNo);
        }
        return ResponseEntity.ok(userMetadata);
    }
}
