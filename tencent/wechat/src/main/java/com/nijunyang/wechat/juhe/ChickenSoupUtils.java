package com.nijunyang.wechat.juhe;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONObject;

/**
 * Description:
 * Created by nijunyang on 2023/3/26 20:06
 */
public class ChickenSoupUtils {

    private static String url = "https://apis.juhe.cn/fapig/soup/query?key=0b6e1df27ec5d4ff747ba5d1df225c4d";


    public static String getChickenSoup() {
        String s = HttpUtil.get(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        return jsonObject.getJSONObject("result").getString("text");
    }
}
