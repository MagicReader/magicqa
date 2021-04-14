package com.yanghui.magicqa.entity.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostInfoDetail {
    private long post_id;
    private String name;
    private String title;
    private String context;
    private java.sql.Timestamp time;
    private int state;
}
