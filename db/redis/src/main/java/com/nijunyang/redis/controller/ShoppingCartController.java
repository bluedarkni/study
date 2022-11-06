package com.nijunyang.redis.controller;

import com.nijunyang.redis.model.Token;
import com.nijunyang.redis.model.vo.PresentVo;
import com.nijunyang.redis.model.vo.ShoppingCart;
import com.nijunyang.redis.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * Description:购物车
 * Created by nijunyang on 2020/2/21 16:07
 */
@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    /**
     * 加入购物车
     * @param present
     * @param token
     * @return
     */
    @PostMapping()
    public ResponseEntity<String> add(@RequestBody @Validated PresentVo present, Token token){
        token = new Token();
        token.setEseId(10086L);
        token.setUserId("abc");
        String key = shoppingCartService.add(present, token);
        return ResponseEntity.ok(key);
    }


    /**
     * 查看购物车
     * @param token
     * @return
     */
    @GetMapping()
    public ResponseEntity<ShoppingCart> get(Token token,  @RequestParam String mallCode){
        token = new Token();
        token.setEseId(10086L);
        token.setUserId("abc");
        ShoppingCart shoppingCart = shoppingCartService.get(token, mallCode);
        return ResponseEntity.ok(shoppingCart);
    }

    /**
     * 删除购物车
     * @param token
     * @return
     */
    @DeleteMapping()
    public ResponseEntity<Boolean> delete(Token token, @RequestParam String mallCode){
        token = new Token();
        token.setEseId(10086L);
        token.setUserId("abc");
        boolean delete = shoppingCartService.delete(token, mallCode);
        return ResponseEntity.ok(delete);
    }

    @PatchMapping("/present/{presentId}")
    public ResponseEntity<Void> addOrSubtract(Token token,
                                              @PathVariable Long presentId,
                                              @RequestParam String mallCode,
                                              @RequestParam(required = false) Integer addNumber,
                                              @RequestParam(required = false) Integer subtractNumber){
        token = new Token();
        token.setEseId(10086L);
        token.setUserId("abc");
        shoppingCartService.addOrSubtract(token, presentId, mallCode, addNumber, subtractNumber);
        return ResponseEntity.ok().build();
    }

}
