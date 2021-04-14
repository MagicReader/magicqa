package com.yanghui.magicqa.entity;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class Admin {
  private long admin_id;
  private String name;
  private String password;
}
