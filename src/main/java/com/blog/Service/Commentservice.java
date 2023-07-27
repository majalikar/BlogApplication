package com.blog.Service;

import com.blog.PayLoad.CommentdTO;

import java.util.List;

public interface Commentservice {

    public CommentdTO createComment(long id, CommentdTO commentdTO);

    public List<CommentdTO> getComment(long id);
    public CommentdTO getCommentById(long postId, long id);
     public CommentdTO updateComment(long postId, long id, CommentdTO commentDto);

    void deleteComment(long postId, long id);
}
