package com.yanghui.magicqa.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Certification {
  private long certification_id;
  private String phone_number;
  private long id_number;
  private String real_name;
  private String school;
  private String major;
  private String student_card_photo_src;
  private int state;
}
