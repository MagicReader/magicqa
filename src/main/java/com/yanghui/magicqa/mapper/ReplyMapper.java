package com.yanghui.magicqa.mapper;

import com.yanghui.magicqa.entity.Reply;
import com.yanghui.magicqa.entity.dto.ReplyInfo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReplyMapper {
    List<ReplyInfo> select_all_by_uid(Long uid, Long offset, Long pageSize);
    List<ReplyInfo> select_all_by_from_uid(Long from_uid,Long offset,Long pageSize);
    List<ReplyInfo> select_all_by_comment_id(Long comment_id,Long offset,Long pageSize);
    Integer insert_one(Reply replyInfo);
    Long count(String keyword);
    List<ReplyInfo> select_by_pager(long offset, long pageSize, String keyword);
}
