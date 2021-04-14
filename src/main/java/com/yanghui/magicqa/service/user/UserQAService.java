package com.yanghui.magicqa.service.user;

import com.yanghui.magicqa.entity.Comment;
import com.yanghui.magicqa.entity.Post;
import com.yanghui.magicqa.entity.Reply;
import com.yanghui.magicqa.entity.dto.CommentInfo;
import com.yanghui.magicqa.entity.dto.MessageInfo;
import com.yanghui.magicqa.entity.dto.ReplyInfo;
import com.yanghui.magicqa.mapper.CertificationMapper;
import com.yanghui.magicqa.mapper.CommentMapper;
import com.yanghui.magicqa.mapper.PostMapper;
import com.yanghui.magicqa.mapper.ReplyMapper;
import com.yanghui.magicqa.utils.MessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserQAService {
    @Autowired
    PostMapper postMapper;
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CertificationMapper certificationMapper;
    @Autowired
    MessageFactory messageFactory;

    //获取帖子基本信息
    public HashMap getPostInfo(Long currentPage, Long pageSize, String keyword){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 511;
        // 获取分页后的当前页信息和总页数
        try{
            responseBody.put("postInfo",postMapper.select_by_pager((currentPage-1)*pageSize,pageSize,keyword));
        }catch (Exception e){
            System.out.println(e);
            status_code = 512;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //获取帖子详细信息
    public HashMap getPostInfoDetail(Long post_id){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 521;
        try{
            responseBody.put("postInfoDetail",postMapper.select_by_post_id(post_id).get(0));
        }catch (Exception e){
            System.out.println(e);
            status_code = 522;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //获取评论信息
    public HashMap getCommentInfo(Long post_id, Long currentPage, Long pageSize){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 531;
        // 获取分页后的当前页信息和总页数
        try{
            responseBody.put("commentInfo",commentMapper.select_all_by_post_id(post_id, (currentPage-1)*pageSize, pageSize));
        }catch (Exception e){
            System.out.println(e);
            status_code = 532;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //通过 uid或通过comment_id 获取所有回复信息
    public HashMap getReplyInfo(Long comment_id, Long currentPage, Long pageSize){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 541;
        try{
            if(comment_id != null){
                responseBody.put("replyInfo",replyMapper.select_all_by_comment_id(comment_id, (currentPage-1)*pageSize, pageSize));
            }else{
                status_code = 542; // 无id参数
            }
        }catch (Exception e){
            System.out.println(e);
            status_code = 543;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //获取消息
    public HashMap getMessageInfo(Long uid, Long currentPage, Long pageSize){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 551;
        try{ // 两个List 已经按照时间进行降序排序
            List<CommentInfo> commentInfo = commentMapper.select_all_by_uid(uid,0L,currentPage*pageSize);
            List<ReplyInfo> replyInfo =  replyMapper.select_all_by_from_uid(uid,0L,currentPage*pageSize);
            // 两个List 按照时间 归并排序,合并成 List<MessageInfo>
            List<MessageInfo> messageInfo = messageFactory.mergeSortAndSelect(commentInfo,replyInfo,currentPage.intValue(),pageSize.intValue());
            responseBody.put("messageInfo", messageInfo);
        }catch (Exception e){
            System.out.println(e);
            status_code = 552;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //发帖
    public HashMap sendPost(Post postInfo){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 561;
        try{
            postInfo.setTime(new Timestamp(new Date().getTime()));
            if(certificationMapper.select_by_uid(postInfo.getUid()).get(0).getState()!=4){
                status_code = 564; // 未认证，无发言资格
            }
            else if(postMapper.insert_one(postInfo)==0){
                status_code = 562;
            }
        }catch (Exception e){
            System.out.println(e);
            status_code = 563;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //评论
    public HashMap sendComment(Comment commentInfo){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 571;
        try{
            commentInfo.setTime(new Timestamp(new Date().getTime()));
            if(certificationMapper.select_by_uid(commentInfo.getUid()).get(0).getState()!=4){
                status_code = 574; // 未认证，无发言资格
            }
            else if(commentMapper.insert_one(commentInfo)==0){
                status_code = 572;
            }
        }catch (Exception e){
            System.out.println(e);
            status_code = 573;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //回复
    public HashMap sendReply(Reply replyInfo){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 581;
        try{
            replyInfo.setTime(new Timestamp(new Date().getTime()));
            if(certificationMapper.select_by_uid(replyInfo.getFrom_uid()).get(0).getState()!=4){
                status_code = 584; // 未认证，无发言资格
            }
            else if(replyMapper.insert_one(replyInfo)==0){
                status_code = 582;
            }
        }catch (Exception e){
            System.out.println(e);
            status_code = 583;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
}
