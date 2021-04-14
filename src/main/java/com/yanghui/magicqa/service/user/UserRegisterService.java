package com.yanghui.magicqa.service.user;

import com.yanghui.magicqa.mapper.CertificationMapper;
import com.yanghui.magicqa.mapper.UserMapper;
import com.yanghui.magicqa.utils.SmsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class UserRegisterService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CertificationMapper certificationMapper;

    public HashMap register_1(String name, long phone_number){
        HashMap<Object, Object> responseBody = new HashMap<>();
        Integer status_code = 111;
        try{
            if(userMapper.select_all_by_name(name).size()>0){ //验证name是否已存在
                status_code = 112;
            }else if(certificationMapper.select_all_by_phone_number(phone_number).size()>0){ //验证phone_number是否已存在
                status_code = 113;
            }else{
                SmsFactory smsFactory = new SmsFactory();
                String code = smsFactory.produceCode(phone_number); // 缓存手机验证码
                if(code == null){
                    status_code = 114;
                }else if(!smsFactory.sendPhoneMessage(phone_number,code)){ // 发送手机短信
                    status_code = 115;
                }
            }
        }catch (Exception e){
            System.out.println("register_1-其他原因导致失败："+e);
            status_code = 116;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }

    public HashMap register_2(long phone_number, String code){
        HashMap<Object, Object> responseBody = new HashMap<>();
        Integer status_code = 121;
        try{
            if (code == null || redisTemplate.opsForValue().get(String.valueOf(phone_number)) != code) {
                System.out.println("register_2-短信验证码校验失败");
                status_code = 122;
            }
        }catch (Exception e){
            System.out.println("register_2-其他原因导致失败："+e);
            status_code = 123;
        }
        responseBody.put("status_code", status_code);
        return responseBody;
    }

    public HashMap register_3(String name, String password, long phone_number){
        HashMap<Object, Object> responseBody = new HashMap<>();
        Integer status_code = 131;
        try{
            if(userMapper.insert_one(name, password) != 1) {
                status_code = 132; //新建用户失败
            }else if(certificationMapper.insert_one_with_phone_number(phone_number) != 1){
                status_code = 133; //登记手机号失败
            }
        }catch (Exception e){
            System.out.println("register_3-其他原因导致失败："+e);
            status_code = 134;
        }
        responseBody.put("status_code", status_code);
        return responseBody;
    }
}