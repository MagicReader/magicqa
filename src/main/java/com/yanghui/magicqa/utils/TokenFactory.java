package com.yanghui.magicqa.utils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 生产token,如果同一用户重复登录，token的时效重置
 **/
@Service
public class TokenFactory {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String produceRefreshToken(String name,String password) {

        String tokenValue = name+password+"#RefreshToken"+new Timestamp(new Date().getTime()).toString();
        System.out.println("produceRefreshToken:"+tokenValue);
        try{
            //将登陆的信息保存入redis
            redisTemplate.opsForValue().set(tokenValue, "flag");
            //设置token有效的时间 expire方法
            redisTemplate.expire(tokenValue, 300, TimeUnit.SECONDS);
        }catch (Exception e){
            System.out.println("produceRefreshToken error:"+e);
        }
        return tokenValue;
    }

    public String produceAccessToken(String refreshToken) {
        String tokenValue = refreshToken+"##AccessToken"+new Timestamp(new Date().getTime()).toString();
        System.out.println("produceAccessToken:"+tokenValue);
        try{
            //将登陆的信息保存入redis
            redisTemplate.opsForValue().set(tokenValue, "flag");
            //设置token有效的时间 expire方法
            redisTemplate.expire(tokenValue, 7, TimeUnit.DAYS);
        }catch (Exception e){
            System.out.println("produceAccessToken error:"+e);
        }
        return tokenValue;
    }

}
