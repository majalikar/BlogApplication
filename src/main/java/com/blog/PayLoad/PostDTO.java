package com.blog.PayLoad;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDTO {
    private long id;
    @NotEmpty
    @Size(min = 2, message = "Post Ɵtle should have at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post description shouild atleast consist 10 characters")
    private String description;
    @NotEmpty
    private String content;
}
