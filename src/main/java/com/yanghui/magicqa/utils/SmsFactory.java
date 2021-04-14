package com.yanghui.magicqa.utils;


import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 生产短信验证码
 **/
@Service
public class SmsFactory {
    @Autowired
    private StringRedisTemplate redisTemplate;

    public String produceCode(String phone_number) {
        String code = null;
        try{
            //随机n位生成短信验证码
            code = this.produceRandomStr(4);
            //将短信验证码的信息保存入redis
            redisTemplate.opsForValue().set(phone_number, code);
            //设置token有效的时间 expire方法
            redisTemplate.expire(phone_number, 60, TimeUnit.SECONDS);
            System.out.println("produceCode "+"code:"+code);
        }catch (Exception e){
            System.out.println("produceCode fail!");
        }
        return code;
    }

    public boolean sendPhoneMessage(String phone_number,String code) {
        try{

            Credential cred = new Credential("", "");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);

            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet1 = {"+86"+phone_number};
            req.setPhoneNumberSet(phoneNumberSet1);

            req.setTemplateID("922330");
            req.setSign("MagicQA");

            String[] templateParamSet1 = {code};
            req.setTemplateParamSet(templateParamSet1);

            req.setSmsSdkAppid("1400506434");

            SendSmsResponse resp = client.SendSms(req);

            System.out.println(SendSmsResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            System.out.println("sendPhoneMessage fail!");
            return false;
        }
        return true;
    }

    private static String produceRandomStr(int n){
        String str = "0,1,2,3,4,5,6,7,8,9";
        String str2[] = str.split(",");//将字符串以,分割
        Random rand = new Random();//创建Random类的对象rand
        int index = 0;
        String randStr = "";//创建内容为空字符串对象randStr
        for (int i=0; i<n; ++i){
            index = rand.nextInt(str2.length-1);//在0到str2.length-1生成一个伪随机数赋值给index
            randStr += str2[index];//将对应索引的数组与randStr的变量值相连接
        }
        return randStr;
    }

}
