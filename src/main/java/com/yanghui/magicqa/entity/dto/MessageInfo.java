package com.yanghui.magicqa.entity.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageInfo {
    private Long post_id;
    private String post_title;
    private String from_name;
    private String context;
    private java.sql.Timestamp time;
    private String infoType; // "comment"、"reply"
    private int state;
}
