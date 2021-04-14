package com.yanghui.magicqa.controller;

import com.yanghui.magicqa.entity.Announcement;
import com.yanghui.magicqa.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     * 登陆成功返回token给前台，之后每调一个接口都需要校验token之后有效
     * @param name
     * @param password
     * @return status_code
     */

    @PostMapping(value = "/login")
    public @ResponseBody
    Object login(@RequestParam("name") String name, @RequestParam("password") String password){
        return adminService.login(name, password);
    }

    /**
     * 查看用户的基本信息
     * @param currentPage,pageSize,name
     * @return status_code,userInfo
     */
    @GetMapping("/userInfo")
    public @ResponseBody
    Object getUserInfo(@RequestParam("currentPage") Long currentPage,@RequestParam("pageSize") Long pageSize, @RequestParam("name") String name) {
        return adminService.getUserInfo(currentPage, pageSize, name);
    }

    /**
     * 查看用户的详细信息
     * @param uid
     * @return status_code,userInfo,certificationInfo,postInfo,replyInfo,commentInfo
     */
    @GetMapping("/userInfoDetail")
    public @ResponseBody
    Object getUserInfoDetail(@RequestParam("uid") Long uid) {
        return adminService.getUserInfoDetail(uid);
    }

    /**
     * 管理员发送公告
     * @param admin_id,title,context
     * @return status_code
     */
    @PostMapping(value = "/sendAnnouncement")
    public @ResponseBody
    Object sendAnnouncement(Announcement announcementInfo){
        return adminService.sendAnnouncement(announcementInfo);
    }

    /**
     * 获取一个待审核数据
     * @param
     * @return status_code
     */
    @GetMapping(value = "/startCertification")
    public @ResponseBody
    Object startCertification(){
        return adminService.startCertification();
    }
    /**
     * 完成一个审核
     * @param certification_id,state
     * @return status_code
     */
    @PostMapping(value = "/finishCertification")
    public @ResponseBody
    Object finishCertification(Long certification_id,int state){
        return adminService.finishCertification(certification_id, state);
    }
    /**
     * 查看帖子的基本信息
     * @param currentPage,pageSize,keyword
     * @return status_code,postInfo,total
     */
    @GetMapping("/postInfo")
    public @ResponseBody
    Object getPostInfo(@RequestParam("currentPage") Long currentPage,@RequestParam("pageSize") Long pageSize, @RequestParam("keyword") String keyword) {
        return adminService.getPostInfo(currentPage, pageSize, keyword);
    }
    /**
     * 查看评论的基本信息
     * @param currentPage,pageSize,keyword
     * @return status_code,userInfo
     */
    @GetMapping("/commentInfo")
    public @ResponseBody
    Object getCommentInfo(@RequestParam("currentPage") Long currentPage,@RequestParam("pageSize") Long pageSize, @RequestParam("keyword") String keyword) {
        return adminService.getCommentInfo(currentPage, pageSize, keyword);
    }
    /**
     * 查看回复的基本信息
     * @param currentPage,pageSize,keyword
     * @return status_code,userInfo
     */
    @GetMapping("/replyInfo")
    public @ResponseBody
    Object getReplyInfo(@RequestParam("currentPage") Long currentPage,@RequestParam("pageSize") Long pageSize, @RequestParam("keyword") String keyword) {
        return adminService.getReplyInfo(currentPage, pageSize, keyword);
    }

}
