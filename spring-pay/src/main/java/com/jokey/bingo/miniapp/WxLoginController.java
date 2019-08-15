package com.jokey.bingo.miniapp;

import com.jokey.bingo.common.JsonUtil;
import com.jokey.bingo.common.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JokeyFeng
 * date:2018/12/17
 * project:spring-boot
 * package:com.jokey.bingo.miniapp
 * comment:
 */
@RestController
public class WxLoginController {

    @Autowired
    private RedisOperator redis;

    @PostMapping("/wxLogin")
    public JsonResult wxLogin(String code) {

        System.out.println("wxlogin - code: " + code);

//		https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wxa2049f5aead89372");
        param.put("secret", "3a62d9b55028c644bacdd8412fada021");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");
        //HttpClientUtil.doGet(url, param)
        String wxResult = "";
        System.out.println(wxResult);

        WXSessionModel model = JsonUtil.jsonToPojo(wxResult, WXSessionModel.class);
        // 存入session到redis
        redis.set("user-redis-session:" + model.getOpenid(), model.getSession_key(), 1000 * 60 * 30);
        return JsonResult.ok();
    }
}
