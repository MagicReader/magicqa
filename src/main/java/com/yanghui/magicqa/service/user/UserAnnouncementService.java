package com.yanghui.magicqa.service.user;

import com.yanghui.magicqa.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class UserAnnouncementService {
    @Autowired
    AnnouncementMapper announcementMapper;

    //获取公告基本信息
    public HashMap getAnnouncementInfo(Long currentPage, Long pageSize){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 411;
        // 获取分页后的当前页信息和总页数
        try{
            responseBody.put("announcementInfo",announcementMapper.select_by_pager((currentPage-1)*pageSize,pageSize));
        }catch (Exception e){
            System.out.println(e);
            status_code = 412;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }
    //获取公告详细信息
    public HashMap getAnnouncementInfoDetail(Long announcement_id){
        HashMap<String, Object> responseBody = new HashMap<>();
        Integer status_code = 421;
        // 获取分页后的当前页信息和总页数
        try{
            responseBody.put("announcementInfoDetail",announcementMapper.select_by_announcement_id(announcement_id).get(0));
        }catch (Exception e){
            System.out.println(e);
            status_code = 422;
        }finally {
            responseBody.put("status_code", status_code);
        }
        return responseBody;
    }

}
