package com.yanghui.magicqa.entity.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostInfo {
    private long post_id;
    private String name;
    private String title;
    private java.sql.Timestamp time;
    private int comment_number;
    private int state;
}
