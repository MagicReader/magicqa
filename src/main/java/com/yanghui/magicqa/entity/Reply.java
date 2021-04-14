package com.yanghui.magicqa.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Reply {

  private long reply_id;
  private long from_uid;
  private long to_uid ;
  private long comment_id;
  private String context;
  private java.sql.Timestamp time;
  private int from_state;
  private int to_state;
}
