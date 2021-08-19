package com.learn.service;

import java.util.Map;

/**
 * 一些声明信息
 * Description: <br/>
 * date: 2020/7/10 18:26<br/>
 *
 * @author 李佳乐<br/>
 * @since JDK 1.8
 */
public interface SendSms {

    /**
     * 给阿里云发送短信验证码
     */
    boolean send(String phoneNum, String templateCode, Map<String,Object> code);
}
