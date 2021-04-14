package com.yanghui.magicqa.mapper;

import com.yanghui.magicqa.entity.Announcement;
import com.yanghui.magicqa.entity.dto.AnnouncementDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementMapper {
    Integer insert_one(Announcement announcement);
    List<Announcement> select_by_pager(Long offset, Long pageSize);
    List<AnnouncementDetail> select_by_announcement_id(Long select_by_announcement_id);
}
