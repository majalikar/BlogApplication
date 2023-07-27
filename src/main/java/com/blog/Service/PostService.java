package com.blog.Service;

import com.blog.PayLoad.PostDTO;
import com.blog.PayLoad.PostResponse;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postdto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortby, String sortDIR);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO, long id);

    void deletePost(long id);
}
