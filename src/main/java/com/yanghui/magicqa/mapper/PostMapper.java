package com.yanghui.magicqa.mapper;

import com.yanghui.magicqa.entity.Post;
import com.yanghui.magicqa.entity.dto.PostInfo;
import com.yanghui.magicqa.entity.dto.PostInfoDetail;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostMapper {
    List<Post> select_all_by_uid(long uid);
    Long count(String keyword);
    List<PostInfo> select_by_pager(long offset, long pageSize, String keyword);
    List<PostInfoDetail> select_by_post_id(long post_id);
    List<PostInfo> select_by_comment_id(Long comment_id);
    List<PostInfo> select_by_reply_id(Long reply_id);
    int insert_one(Post postInfo);
}
