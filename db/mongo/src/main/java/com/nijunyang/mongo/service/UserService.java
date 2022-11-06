package com.nijunyang.mongo.service;

import com.mongodb.BasicDBObject;
import com.nijunyang.aspect.annotation.SensitiveInfo;
import com.nijunyang.aspect.annotation.SensitiveMethod;
import com.nijunyang.mongo.model.User;
import com.nijunyang.mongo.model.UserMetadata;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;

/**
 * Description:
 * Created by nijunyang on 2020/4/30 14:04
 */
@Service
public class UserService {

    @Autowired
    MongoTemplate mongoTemplate;

//    @PostConstruct
//    public void init(){
//
//    }


    @SensitiveMethod
    public String add(@SensitiveInfo UserMetadata userMetadata) {
        userMetadata.setCreatedTime(Instant.now());
        userMetadata.setUpdatedTime(Instant.now());
        User user = new User();
        user.setUserMetadata(userMetadata);
        mongoTemplate.save(user);//若主键存在则进行修改
//        mongoTemplate.insert(user); insert主键已经存在 抛错，不插入数据
        //批操作，insert一次插入 save是遍历插入
        return user.getId();
    }

    @SensitiveMethod
    public UserMetadata getUserMetadataById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
//        User user = mongoTemplate.findOne(query, User.class);
        User user = mongoTemplate.findById(new ObjectId(id), User.class);
        return user.getUserMetadata();
    }

    @SensitiveMethod
    public UserMetadata getUserMetadataByCard(@SensitiveInfo String cardNo) {
        System.out.println(cardNo);
        Query query = new Query();
        query.addCriteria(Criteria.where("userMetadata.idCardNo").is(cardNo));
        return mongoTemplate.findOne(query, User.class).getUserMetadata();

    }

    @SensitiveMethod
    public UserMetadata getUserMetadataByPhone(@SensitiveInfo String phoneNo) {


        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put("userMetadata.phoneNumber", phoneNo); //查询条件
        BasicDBObject fieldsObject = new BasicDBObject(); //查询字段
        fieldsObject.put("userMetadata", true);
        Query query = new BasicQuery(queryObject.toJson(), fieldsObject.toJson());
        Object object = mongoTemplate.findOne(query, UserMetadata.class);
        UserMetadata userMetadata = mongoTemplate.findOne(query, UserMetadata.class);
        return userMetadata;
    }
}
