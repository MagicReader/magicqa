package com.yanghui.magicqa.controller;

import com.yanghui.magicqa.entity.Comment;
import com.yanghui.magicqa.entity.Post;
import com.yanghui.magicqa.entity.Reply;
import com.yanghui.magicqa.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserAnnouncementService userAnnouncementService;
    @Autowired
    private UserQAService userQAService;
    @Autowired
    private UserCertificationService userCertificationService;
    //测试
    @PostMapping(value = "/testAPI")
    public @ResponseBody
    Object testAPI(@RequestParam("state") String state){
        return userLoginService.testAPI(state);
    }
    // 用户登录
    @PostMapping(value = "/login")
    public @ResponseBody
    Object login(@RequestParam("name") String name, @RequestParam("password") String password){
        return userLoginService.login(name, password);
    }
    // 用户注册
    @PostMapping(value = "/register/1")
    public @ResponseBody
    Object register_1(@RequestParam("name") String name, @RequestParam("phone_number") String phone_number){
        return userRegisterService.register_1(name, phone_number);
    }
    @PostMapping(value = "/register/2")
    public @ResponseBody
    Object register_2(@RequestParam("phone_number") String phone_number, @RequestParam("code") String code){
        return userRegisterService.register_2(phone_number, code);
    }
    @PostMapping(value = "/register/3")
    public @ResponseBody
    Object register_3(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("phone_number") String phone_number){
        return userRegisterService.register_3(name, password, phone_number);
    }
    //用户获取公告信息
    @GetMapping(value = "/announcementInfo")
    public @ResponseBody
    Object announcementInfo(@RequestParam("currentPage") Long currentPage,@RequestParam("pageSize") Long pageSize){
        return userAnnouncementService.getAnnouncementInfo(currentPage, pageSize);
    }
    @GetMapping(value = "/announcementInfoDetail")
    public @ResponseBody
    Object announcementInfoDetail(@RequestParam("announcement_id") Long announcement_id){
        return userAnnouncementService.getAnnouncementInfoDetail(announcement_id);
    }
    //用户获取主页信息
    @GetMapping(value = "/postInfo")
    public @ResponseBody
    Object postInfo(@RequestParam("currentPage") Long currentPage,@RequestParam("pageSize") Long pageSize,@RequestParam("keyword") String keyword){
        return userQAService.getPostInfo(currentPage, pageSize, keyword);
    }
    //用户获取帖子详细信息
    @GetMapping(value = "/postInfoDetail")
    public @ResponseBody
    Object postInfoDetail(@RequestParam("post_id") Long post_id){
        return userQAService.getPostInfoDetail(post_id);
    }
    //用户获取评论信息
    @GetMapping(value = "/commentInfo")
    public @ResponseBody
    Object commentInfoDetail(@RequestParam("currentPage") Long currentPage,@RequestParam("pageSize") Long pageSize,@RequestParam("post_id") Long post_id){
        return userQAService.getCommentInfo(post_id, currentPage, pageSize);
    }
    //用户获取回复信息
    @GetMapping(value = "/replyInfo")
    public @ResponseBody
    Object replyInfo(@RequestParam("currentPage") Long currentPage,@RequestParam("pageSize") Long pageSize,@RequestParam("comment_id") Long comment_id){
        return userQAService.getReplyInfo(comment_id, currentPage, pageSize);
    }
    //用户获取消息信息
    @GetMapping(value = "/messageInfo")
    public @ResponseBody
    Object messageInfo(@RequestParam("currentPage") Long currentPage,@RequestParam("pageSize") Long pageSize,@RequestParam("uid") Long uid){
        return userQAService.getMessageInfo(uid, currentPage, pageSize);
    }
    //用户发帖
    @PostMapping(value = "/sendPost")
    public @ResponseBody
    Object sendPost(Post postInfo){
        return userQAService.sendPost(postInfo);
    }
    //用户评论
    @PostMapping(value = "/sendComment")
    public @ResponseBody
    Object sendComment(Comment commentInfo){
        return userQAService.sendComment(commentInfo);
    }
    //用户回复
    @PostMapping(value = "/sendReply")
    public @ResponseBody
    Object sendReply(Reply replyInfo){
        return userQAService.sendReply(replyInfo);
    }
    //获取身份认证状态
    @GetMapping(value = "/certificationInfo")
    public @ResponseBody
    Object certificationInfo(@RequestParam("uid") Long uid){
        return userCertificationService.getCertificationInfo(uid);
    }
    //身份认证信息上传
    @PostMapping(value = "/certificate")
    public @ResponseBody
    Object certificate(@RequestParam("uid") Long uid,
                       @RequestParam("id_number") Long id_number,
                       @RequestParam("real_name") String real_name,
                       @RequestParam("school") String school,
                       @RequestParam("major") String major,
                       @RequestParam("student_card_photo_src") String student_card_photo_src){
        return userCertificationService.setCertificationInfo(uid,id_number,real_name,school,major,student_card_photo_src);
    }
}
