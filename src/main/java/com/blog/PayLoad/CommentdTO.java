package com.blog.PayLoad;

import lombok.Data;

@Data
public class CommentdTO {
    private Long id;
    private String name;
    private String email;
    private String body;
}
