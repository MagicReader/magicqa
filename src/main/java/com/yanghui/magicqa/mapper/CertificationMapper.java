package com.yanghui.magicqa.mapper;

import com.yanghui.magicqa.entity.Certification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationMapper {
    List<Certification> select_all_by_phone_number(String phone_number);
    List<Certification> select_all_by_certification_id(long certification_id);
    Integer insert_one_with_phone_number(String phone_number);
    List<Certification> select_by_uid(long uid);
    List<Certification> select_one_not_start();
    Integer update_one_state_by_certification_id(Long certification_id,int state);
    Integer update_one_with_more_info(Long uid,Long id_number,String real_name,String school,String major,String student_card_photo_src);
}
