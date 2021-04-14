package com.yanghui.magicqa.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Comment {

  private long comment_id;
  private long uid;
  private long post_id;
  private String context;
  private java.sql.Timestamp time;
  private int state;

}
