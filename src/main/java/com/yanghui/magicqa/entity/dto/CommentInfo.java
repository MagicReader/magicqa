package com.yanghui.magicqa.entity.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentInfo {
    private long comment_id;
    private long uid;
    private String name;
    private String context;
    private java.sql.Timestamp time;
    private int reply_number;
    private int state;
}
