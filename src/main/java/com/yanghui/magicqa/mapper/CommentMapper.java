package com.yanghui.magicqa.mapper;

import com.yanghui.magicqa.entity.Comment;
import com.yanghui.magicqa.entity.dto.CommentInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {
    List<CommentInfo> select_all_by_uid(Long uid, Long offset, Long pageSize);
    List<CommentInfo> select_all_by_post_id(Long post_id, Long offset, Long pageSize);
    int insert_one(Comment commentInfo);
    Long count(String keyword);
    List<CommentInfo> select_by_pager(long offset, long pageSize, String keyword);
}
