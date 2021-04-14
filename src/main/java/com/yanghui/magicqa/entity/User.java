package com.yanghui.magicqa.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

  private long uid;
  private long certification_id;
  private String name;
  private String password;
  private String state;

}
