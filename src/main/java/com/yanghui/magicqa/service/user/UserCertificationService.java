package com.yanghui.magicqa.service.user;

import com.yanghui.magicqa.mapper.CertificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Service
public class UserCertificationService {
    @Autowired
    CertificationMapper certificationMapper;
    //获取身份认证状态
    public HashMap getCertificationInfo(Long uid){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 211;
        // 获取分页后的当前页信息和总页数
        try{
            responseBody.put("certificationInfo",certificationMapper.select_by_uid(uid).get(0));
        }catch (Exception e){
            System.out.println(e);
            status_code = 212;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //身份认证信息上传
    public HashMap setCertificationInfo(Long uid,Long id_number,String real_name,String school,String major,String student_card_photo_src){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 221;
        try{
            certificationMapper.update_one_with_more_info(uid,id_number,real_name,school,major,student_card_photo_src);
        }catch (Exception e){
            System.out.println(e);
            status_code = 222;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
}
