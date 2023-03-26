package com.nijunyang.wechat.lovepush;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.nijunyang.wechat.juhe.ChickenSoupUtils;
import com.nijunyang.wechat.pubsub.WechatMsgTemplateMRequest;
import com.nijunyang.wechat.util.DateUtils;
import com.nijunyang.wechat.juhe.WeatherUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by nijunyang on 2023/3/24 21:22
 */
public class WechatApp {
    private static Map<Week, String> weekMap = new HashMap<>();

    static {
        weekMap.put(Week.MONDAY, "星期一");
        weekMap.put(Week.TUESDAY, "星期二");
        weekMap.put(Week.WEDNESDAY, "星期三");
        weekMap.put(Week.THURSDAY, "星期四");
        weekMap.put(Week.FRIDAY, "星期五");
        weekMap.put(Week.SATURDAY, "星期六");
        weekMap.put(Week.SUNDAY, "星期日");
    }

    /**
     * access_token的有效期目前为2个小时  {"access_token":"ACCESS_TOKEN","expires_in":7200}
     */
    private static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?" +
            "grant_type=client_credential&appid=wxe8e147f1e60680e2&secret=6adefe3c1de89425ddbeface3d99ad86";


    private static String myOpenId = "oSrPm5pct_BDqhKBjlAO5_BKZUB4";
    private static String sheOpenId = "oSrPm5qRZqUCbrF8wutwJPMx6YDk";

    private static List<String> openIds = Arrays.asList("oSrPm5pct_BDqhKBjlAO5_BKZUB4", "oSrPm5qRZqUCbrF8wutwJPMx6YDk");
//    private static List<String> openIds = Arrays.asList("oSrPm5pct_BDqhKBjlAO5_BKZUB4");

    private static String templateId = "PQfpcdgKJSkeKlthsd-xcAZh4QNLCShQn_BXhT5uhps";


//    private static String token = "67_kOUT2YyOHQOY7jMLMZz_Zpb5Z0pwtuRcEJy-XP1jfcVw0Ceyofd6u0U2QXfLoG42SXjfNRwxGHqSDYEPTHaRXDqyLHWnaG610gWAZsd_9Fwo3aHnLVIlYyITRO0WSLeAAAOKD";
    private static String sendUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    public static void main(String[] args) {
        main00();
    }

    public static void main00() {
        openIds.forEach(WechatApp::main000);
    }
    public static void main000(String openid) {
        String tokenStr = HttpUtil.get(TOKEN_URL);
        JSONObject jsonObjectToken = JSONObject.parseObject(tokenStr);
        String token = jsonObjectToken.getString("access_token");

        /**
         {"access_token":"67_-B3qlE8J3CXix3S85EyGs8l6xL_mNBhOsOHe186v638XCbRCZdqUZrBIKQQVNye-kZ4tKkSf8m9AZ5Hi1GLX_b2Gh00Z3IrTQiMPY-23kPev86G0zMQWXyXMUowNUKjAJAUBR","expires_in":7200}
         */
        Date today = new Date();
        Date marryDay = DateUtil.parseDateTime("2022-06-06 00:00:00");
//        Date birthday = DateUtil.parseDateTime("2022-06-17 00:00:00");
        Date pregnantDay = DateUtil.parseDateTime("2022-11-30 00:00:00");
        Date bornDay = DateUtil.parseDateTime("2023-09-06 00:00:00");
        WechatMsgTemplateMRequest<Object> templateMRequest = new WechatMsgTemplateMRequest<>();
//        templateMRequest.setTouser(myOpenId);
        templateMRequest.setTouser(openid);
        templateMRequest.setTemplate_id(templateId);
        JSONObject jsonObjectOut = new JSONObject();
        JSONObject jsonObjectDate = new JSONObject();
        jsonObjectDate.putIfAbsent("value", DateUtil.format(today, "yyyy-MM-dd") + "  " + weekMap.get(DateUtil.dayOfWeekEnum(today)));
        jsonObjectDate.putIfAbsent("color", "#173177");
        jsonObjectOut.putIfAbsent("dateweek", jsonObjectDate);

        JSONObject jsonObjectArea = new JSONObject();
        jsonObjectArea.putIfAbsent("value", "成都");
        jsonObjectArea.putIfAbsent("color", "#173177");
        jsonObjectOut.putIfAbsent("area", jsonObjectArea);

        JSONObject jsonObject = WeatherUtils.getFuture("成都");

        JSONObject jsonObjectWeather = new JSONObject();
        jsonObjectWeather.putIfAbsent("value", jsonObject.get("weather"));
        jsonObjectWeather.putIfAbsent("color", "#173177");
        jsonObjectOut.putIfAbsent("weather", jsonObjectWeather);

        JSONObject jsonObjectTemperature = new JSONObject();
        jsonObjectTemperature.putIfAbsent("value", jsonObject.get("temperature"));
        jsonObjectTemperature.putIfAbsent("color", "#173177");
        jsonObjectOut.putIfAbsent("temperature", jsonObjectTemperature);

        JSONObject jsonObjectMarry = new JSONObject();
        jsonObjectMarry.putIfAbsent("value", DateUtil.between(today, marryDay, DateUnit.DAY, true));
        jsonObjectMarry.putIfAbsent("color", "#173177");
        jsonObjectOut.putIfAbsent("marry", jsonObjectMarry);

        JSONObject jsonObjectBirthday = new JSONObject();
        jsonObjectBirthday.putIfAbsent("value", DateUtils.calcNextBirthday(6, 17));
        jsonObjectBirthday.putIfAbsent("color", "#173177");
        jsonObjectOut.putIfAbsent("birthday", jsonObjectBirthday);

        JSONObject jsonObjectPregnant = new JSONObject();
        jsonObjectPregnant.putIfAbsent("value", DateUtil.between(today, pregnantDay, DateUnit.DAY, true));
        jsonObjectPregnant.putIfAbsent("color", "#173177");
        jsonObjectOut.putIfAbsent("pregnant", jsonObjectPregnant);

        JSONObject jsonObjectBorn = new JSONObject();
        jsonObjectBorn.putIfAbsent("value", DateUtil.between(today, bornDay, DateUnit.DAY, true));
        jsonObjectBorn.putIfAbsent("color", "#173177");
        jsonObjectOut.putIfAbsent("born", jsonObjectBorn);

        JSONObject jsonObjectRandomQuotation = new JSONObject();
        jsonObjectRandomQuotation.putIfAbsent("value", ChickenSoupUtils.getChickenSoup());
        jsonObjectRandomQuotation.putIfAbsent("color", "#173177");
        jsonObjectOut.putIfAbsent("randomQuotation", jsonObjectRandomQuotation);


        templateMRequest.setData(jsonObjectOut);

        String response = HttpUtil.post(sendUrl + token, JSONUtil.toJsonStr(templateMRequest));

    }


}
