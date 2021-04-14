package com.yanghui.magicqa.mapper;

import com.yanghui.magicqa.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {
    List<Admin> select_all_by_name_and_password(String name, String password);
}
