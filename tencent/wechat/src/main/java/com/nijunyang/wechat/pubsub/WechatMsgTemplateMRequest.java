package com.nijunyang.wechat.pubsub;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: 微信模板参数
 * Created by nijunyang on 2023/3/24 21:55
 */
@Getter
@Setter
public class WechatMsgTemplateMRequest<T> {

    /**
     * 接收者openid 必须
     */
    private String touser;

    /**
     * 模板ID 必须
     */
    private String template_id;

    /**
     * 模板跳转链接（海外帐号没有跳转能力） 非必须
     */
    private String url;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    private Miniprogram miniprogram;

    /**
     * 防重入id。对于同一个openid + client_msg_id, 只发送一条消息,10分钟有效,超过10分钟不保证效果。若无防重入需求，可不填
     */
    private String client_msg_id;

    /**
     * 模板数据，取决于配置的模板信息
     *
     {{dateweek.DATA}}
     地区：{{area.DATA}}
     天气：{{weather.DATA}}
     气温：{{temperature.DATA}}
     今天是我们结婚的第{{marry.DATA}}天
     今天是你怀孕的第{{pregnant.DATA}}天
     距离和小宝宝见面大约还有{{born.DATA}}天
     每天都要按时吃饭，多喝水哦~~~
     {{randomQuotation.DATA}}
     */
    private T data;


    @Getter
    @Setter
    public static class Miniprogram{

        /**
         * 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
         */
        private String appid;
        /**
         * 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏
         */
        private String pagepath;

    }

    @Getter
    @Setter
    public static class MeMeDa{

        /**
         * 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）
         */
        private String appid;
        /**
         * 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏
         */
        private String pagepath;

    }
}
