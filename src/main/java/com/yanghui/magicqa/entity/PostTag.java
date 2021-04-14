package com.yanghui.magicqa.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PostTag {
    private  long post_tag_id;
    private  long post_id;
    private String tag_name;
}
