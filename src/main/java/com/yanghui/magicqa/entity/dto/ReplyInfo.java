package com.yanghui.magicqa.entity.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReplyInfo {
    private long reply_id;
    private long from_uid;
    private long to_uid;
    private String from_name;
    private String to_name;
    private int from_state;
    private int to_state;
    private String context;
    private java.sql.Timestamp time;
}
