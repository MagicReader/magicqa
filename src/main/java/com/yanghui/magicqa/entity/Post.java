package com.yanghui.magicqa.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Post {

  private long post_id;
  private long uid;
  private String title;
  private String context;
  private java.sql.Timestamp time;
  private int state;

}
