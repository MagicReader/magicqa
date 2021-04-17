package com.yanghui.magicqa.mapper;

import com.yanghui.magicqa.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> select_all_by_name_and_password(String name, String password);
    List<User> select_all_by_state(String state);
    List<User> select_all_by_name(String name);
    List<User> select_all_by_uid(long uid);
    Integer insert_one(String name,String password,Long certification_id);
    List<User> select_by_pager(Long offset, Long pageSize, String name);
    Long count(String name);
}
