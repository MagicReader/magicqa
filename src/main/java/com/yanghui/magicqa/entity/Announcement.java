package com.yanghui.magicqa.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Announcement {

  private long announcement_id;
  private long admin_id;
  private String title;
  private String context;
  private java.sql.Timestamp time;

}
