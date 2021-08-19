package com.learn.controller;

import com.learn.service.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/7/10 18:27<br/>
 *
 * @author ${李佳乐}<br/>
 * @since JDK 1.8
 */
@RestController
@CrossOrigin//跨域支持
@SuppressWarnings("all")
public class SmsApiController {

    @Autowired
    private SendSms sendSms;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{phone}")
    public String code(@PathVariable("phone") String phone) {
        String code = redisTemplate.opsForValue().get("phone");
        if (!StringUtils.isEmpty(code)) {
            return phone + ":" + code + ":" + "验证码已存在";
        }
        code = UUID.randomUUID().toString().substring(0, 6);
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = sendSms.send(phone, "SMS_195861786", param);
        if (isSend && phone.length() > 10) {
            redisTemplate.opsForValue().set("phone", code, 60, TimeUnit.SECONDS);
            return phone + ":" + code + ":" + "发送成功";
        } else {
            return phone + ":" + code + ":" + "发送失败";
        }
    }

}
