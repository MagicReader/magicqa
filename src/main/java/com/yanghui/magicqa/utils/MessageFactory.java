package com.yanghui.magicqa.utils;

import com.yanghui.magicqa.entity.dto.CommentInfo;
import com.yanghui.magicqa.entity.dto.MessageInfo;
import com.yanghui.magicqa.entity.dto.PostInfo;
import com.yanghui.magicqa.entity.dto.ReplyInfo;
import com.yanghui.magicqa.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageFactory {
    @Autowired
    PostMapper postMapper;
    public List<MessageInfo> mergeSortAndSelect(List<CommentInfo> commentInfo, List<ReplyInfo> replyInfo,int currentPage,int pageSize){
        List<MessageInfo> messageInfo = new ArrayList<>();
        int index_comment =0;
        int index_reply =0;
        int size_comment=commentInfo.size();
        int size_reply=replyInfo.size();
        //小于等于则说明数据不够支持下拉新增
        if(size_reply+size_comment>(currentPage-1)*pageSize){
            while(index_comment<size_comment && index_reply<size_reply){ // 两个List 按照时间 归并排序,合并成 List<MessageInfo>
                MessageInfo temp_message = new MessageInfo();
                if(commentInfo.get(index_comment).getTime().after(replyInfo.get(index_reply).getTime())){
                    addCommentMessage(messageInfo,commentInfo.get(index_comment));
                    index_comment++;
                }else{
                    addReplyMessage(messageInfo,replyInfo.get(index_reply));
                    index_reply++;
                }
            }
            while (index_comment<size_comment){
                addCommentMessage(messageInfo,commentInfo.get(index_comment));
                index_comment++;
            }
            while (index_reply<size_reply){
                addReplyMessage(messageInfo,replyInfo.get(index_reply));
                index_reply++;
            }

        }
        // 分页返回数据
        int size_total = messageInfo.size();
        int formIndex = (currentPage-1)*pageSize;
        int toIndex = Math.min(currentPage*pageSize, size_total);
        if(formIndex>size_total){
            return new ArrayList<>();
        }else{
            return messageInfo.subList(formIndex,toIndex);
        }
    }

    private void addCommentMessage(List<MessageInfo> messageInfo,CommentInfo temp_comment){
        MessageInfo temp_message = new MessageInfo();
        PostInfo temp_post = postMapper.select_by_comment_id(temp_comment.getComment_id()).get(0);
        temp_message.setPost_id(temp_post.getPost_id());
        temp_message.setPost_title(temp_post.getTitle());
        temp_message.setFrom_name(temp_comment.getName());
        temp_message.setContext(temp_comment.getContext());
        temp_message.setTime(temp_comment.getTime());
        temp_message.setInfoType("comment");
        messageInfo.add(temp_message);
    }

    private void addReplyMessage(List<MessageInfo> messageInfo,ReplyInfo temp_reply){
        MessageInfo temp_message = new MessageInfo();
        PostInfo temp_post = postMapper.select_by_reply_id(temp_reply.getReply_id()).get(0);
        temp_message.setPost_id(temp_post.getPost_id());
        temp_message.setPost_title(temp_post.getTitle());
        temp_message.setFrom_name(temp_reply.getFrom_name());
        temp_message.setContext(temp_reply.getContext());
        temp_message.setTime(temp_reply.getTime());
        temp_message.setInfoType("reply");
        messageInfo.add(temp_message);
    }
}
