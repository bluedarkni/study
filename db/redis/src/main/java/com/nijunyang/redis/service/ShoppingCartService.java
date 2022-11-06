package com.nijunyang.redis.service;

import com.nijunyang.redis.model.Token;
import com.nijunyang.redis.model.vo.PresentVo;
import com.nijunyang.redis.model.vo.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Description:
 * Created by nijunyang on 2020/2/21 16:42
 */
@Service
public class ShoppingCartService {

    private static final String KEY = "cart:eseId:%d:mall:%s:userId:%s";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, PresentVo> hashOperations;

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    public String add(PresentVo present, Token token){
        String redisKey = String.format(KEY, token.getEseId(), present.getMallCode(), token.getUserId());
        String  presentId = String.valueOf(present.getPresentId());
        boolean hasCurrentElement = hashOperations.hasKey(redisKey, presentId);
        if (!hasCurrentElement) {
            hashOperations.put(redisKey, presentId, present);
        } else {
            PresentVo presentOld = hashOperations.get(redisKey, presentId);
            presentOld.setNumber(presentOld.getNumber() + present.getNumber());
            hashOperations.put(redisKey, presentId, presentOld);
        }
        return redisKey;
    }

    public ShoppingCart get(Token token, String mallCode){
        String redisKey = String.format(KEY, token.getEseId(), mallCode, token.getUserId());
        ShoppingCart shoppingCart = new ShoppingCart();
        Map<String, PresentVo> presentMap = hashOperations.entries(redisKey);
        if (!presentMap.isEmpty()) {
            shoppingCart.setKey(redisKey);
            int pointTotalPrice = 0;
            for (PresentVo present : presentMap.values()) {
                shoppingCart.getPresentList().add(present);
                pointTotalPrice += present.getNumber() * present.getPointUnitPrice();
            }
            shoppingCart.setPointTotalPrice(pointTotalPrice);
        }
        return shoppingCart;
    }

    public boolean delete(Long eseId, String userId, String mallCode){
        String redisKey = String.format(KEY, eseId, mallCode, userId);
        if (redisTemplate.hasKey(redisKey)) {
            return redisTemplate.delete(redisKey);
        }
        return true;
    }

    public boolean delete(Token token, String mallCode) {
        return delete(token.getEseId(), token.getUserId(), mallCode);
    }

    public void addOrSubtract(Token token, Long presentId, String mallCode, Integer addNumber, Integer subtractNumber) {
        if ((addNumber != null && subtractNumber != null) || (addNumber == null && subtractNumber == null)) {
            throw new IllegalArgumentException("addNumber and subtractNumber can only have one.");
        }
        String redisKey = String.format(KEY, token.getEseId(), mallCode, token.getUserId());
        String presentIdStr = String.valueOf(presentId);
        PresentVo presentOld = hashOperations.get(redisKey, presentIdStr);
        if (presentOld != null) {
            if (addNumber != null) {
                presentOld.setNumber(presentOld.getNumber() + addNumber);
                hashOperations.put(redisKey, presentIdStr, presentOld);
            }
            if (subtractNumber != null) {
                int number = presentOld.getNumber() - subtractNumber;
                if (number < 0) {
                    throw new IllegalArgumentException("subtractNumber must be more than " + presentOld.getNumber());
                }
                if (number == 0) {
                    hashOperations.delete(redisKey, presentIdStr);
                    return;
                }
                presentOld.setNumber(number);
                hashOperations.put(redisKey, presentIdStr, presentOld);
            }
        }
    }
}
