package com.yanghui.magicqa.service.user;

import com.yanghui.magicqa.entity.User;
import com.yanghui.magicqa.mapper.UserMapper;
import com.yanghui.magicqa.utils.TokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserLoginService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private TokenFactory tokenFactory;

    public HashMap login(String name, String password){
        HashMap<Object, Object> responseBody = new HashMap<>();
        Integer status_code = 141;
        try{
            List<User> userList = userMapper.select_all_by_name_and_password(name, password);
            if(userList.size()==0){
                status_code = 142; // 用户名或密码错误
            }else{ // 校验成功，生成tokenInfo
                HashMap<String, String> tokenInfo = new HashMap<>();
                tokenInfo.put("refreshToken",tokenFactory.produceRefreshToken(userList.get(0).getName(), userList.get(0).getPassword()));
                tokenInfo.put("accessToken",tokenFactory.produceAccessToken(tokenInfo.get("refreshToken")));
                responseBody.putAll(tokenInfo);
                responseBody.put("uid",userList.get(0).getUid());
            }
        }catch (Exception e){
            System.out.println(e);
            status_code = 143; //其他原因失败
        }
        responseBody.put("status_code", status_code);
        return responseBody;
    }

    public HashMap testAPI(String state){
        HashMap<Object, Object> responseBody = new HashMap<>();
        Integer status_code = 1;
        try{
            List<User> userList = userMapper.select_all_by_state(state);
            if(userList.size()==0){
                status_code = 2; // 无相应的state
            }else{ // 校验成功
                responseBody.put("userList", userList);
            }
        }catch (Exception e){
            System.out.println(e);
            status_code = 3; //其他原因失败
        }
        responseBody.put("status_code", status_code);
        return responseBody;
    }
}
