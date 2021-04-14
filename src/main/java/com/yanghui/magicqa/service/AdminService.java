package com.yanghui.magicqa.service;

import com.yanghui.magicqa.entity.Admin;
import com.yanghui.magicqa.entity.Announcement;
import com.yanghui.magicqa.entity.Certification;
import com.yanghui.magicqa.entity.User;
import com.yanghui.magicqa.mapper.*;
import com.yanghui.magicqa.utils.TokenFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CertificationMapper certificationMapper;
    @Autowired
    AnnouncementMapper announcementMapper;
    @Autowired
    PostMapper postMapper;
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    private TokenFactory tokenFactory;

    //登录
    public HashMap login(String name, String password){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 311;
        // 获取完全匹配name和password的admin对象集合
        List<Admin> adminList = adminMapper.select_all_by_name_and_password(name, password);
        // 用户名或密码 校验失败
        if(adminList.size()==0){
            status_code = 312;
        }else{
            //校验成功，生成tokenInfo
            HashMap<String, String> tokenInfo = new HashMap<>();
            tokenInfo.put("refreshToken",tokenFactory.produceRefreshToken(adminList.get(0).getName(), adminList.get(0).getPassword()));
            tokenInfo.put("accessToken",tokenFactory.produceAccessToken(tokenInfo.get("refreshToken")));
            responseBody.putAll(tokenInfo);
            responseBody.put("admin_id",adminList.get(0).getAdmin_id());
        }
        responseBody.put("status_code", status_code);
        return responseBody;
    }

    //获取用户基本信息
    public HashMap getUserInfo(Long currentPage,Long pageSize, String name){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 321;
        // 获取分页后的当前页信息和总页数
        try{
            responseBody.put("total",userMapper.count(name));
            responseBody.put("userInfo",userMapper.select_by_pager((currentPage-1)*pageSize,pageSize, name));
        }catch (Exception e){
            System.out.println(e);
            status_code = 322;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }

    //获取用户详细信息
    public HashMap getUserInfoDetail(Long uid){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 331;
        // 获取分页后的当前页信息和总页数
        try{
            User userInfo = userMapper.select_all_by_uid(uid).get(0);
            responseBody.put("userInfo",userInfo);
            List<Certification> certificationInfo = certificationMapper.select_all_by_certification_id(userInfo.getCertification_id());
            if(certificationInfo.size()>0){
                responseBody.put("certificationInfo",certificationInfo.get(0));
            }else{
                responseBody.put("certificationInfo",null);
            }
            responseBody.put("postInfo",postMapper.select_all_by_uid(uid));
            responseBody.put("commentInfo",commentMapper.select_all_by_uid(uid,null,null));
            responseBody.put("replyInfo",replyMapper.select_all_by_uid(uid, null, null));
        }catch (Exception e){
            System.out.println(e);
            status_code = 332;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }

    //获取用户详细信息
    public HashMap sendAnnouncement(Announcement announcement){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 341;
        // 获取分页后的当前页信息和总页数
        try{
            announcement.setTime(new Timestamp(new Date().getTime()));
            announcementMapper.insert_one(announcement);
        }catch (Exception e){ // 其他原因失败
            System.out.println(e);
            status_code = 342;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }

    //获取一个待审核数据
    public HashMap startCertification(){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 351;
        // 获取分页后的当前页信息和总页数
        try{
            List<Certification> certificationInfo =  certificationMapper.select_one_not_start();
            if(certificationInfo.size()==0){
                status_code = 353; // 当前无待认证数据
            }else{
                responseBody.put("certificationInfo", certificationInfo.get(0));
            }
        }catch (Exception e){ // 其他原因失败
            System.out.println(e);
            status_code = 352;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //完成一个审核
    public HashMap finishCertification(Long certification_id,int state){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 351;
        // 获取分页后的当前页信息和总页数
        try{
            certificationMapper.update_one_state_by_certification_id(certification_id,state);
        }catch (Exception e){ // 其他原因失败
            System.out.println(e);
            status_code = 352;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //获取帖子的基本信息
    public HashMap getPostInfo(Long currentPage,Long pageSize, String keyword){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 361;
        // 获取分页后的当前页信息和总页数
        try{
            responseBody.put("total",postMapper.count(keyword));
            responseBody.put("postInfo",postMapper.select_by_pager((currentPage-1)*pageSize,pageSize, keyword));
        }catch (Exception e){
            System.out.println(e);
            status_code = 362;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //获取评论的基本信息
    public HashMap getCommentInfo(Long currentPage,Long pageSize, String keyword){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 361;
        // 获取分页后的当前页信息和总页数
        try{
            responseBody.put("total",commentMapper.count(keyword));
            responseBody.put("commentInfo",commentMapper.select_by_pager((currentPage-1)*pageSize,pageSize, keyword));
        }catch (Exception e){
            System.out.println(e);
            status_code = 362;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //获取回复的基本信息
    public HashMap getReplyInfo(Long currentPage,Long pageSize, String keyword){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 361;
        // 获取分页后的当前页信息和总页数
        try{
            responseBody.put("total",replyMapper.count(keyword));
            responseBody.put("replyInfo",replyMapper.select_by_pager((currentPage-1)*pageSize,pageSize, keyword));
        }catch (Exception e){
            System.out.println(e);
            status_code = 362;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
}
