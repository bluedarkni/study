package com.nijunyang.wechat.weather;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.net.URLDecoder;
import java.nio.charset.Charset;

import static javax.swing.UIManager.get;


/**
 * Description:
 * Created by nijunyang on 2023/3/25 22:20
 */
public class WeatherUtils {


    private static String url = "http://apis.juhe.cn/simpleWeather/query?city=%s&key=c5c06a749e4e2fc1f5dbe57efce673ad";

    private static String key = "c5c06a749e4e2fc1f5dbe57efce673ad";


    public static void main(String[] args) {
        getFuture("成都");
    }


    public static JSONObject getFuture(String city) {

        String urlR =  String.format(url, URLEncodeUtil.encode(city, Charset.defaultCharset()));
        String s = HttpUtil.get(urlR);
        JSONObject jsonObject = JSONObject.parseObject(s);
        return jsonObject.getJSONObject("result").getJSONArray("future").getJSONObject(0);


    }
}
