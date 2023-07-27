package com.blog.Repositries;

import com.blog.Entities.Comment;
import com.blog.PayLoad.CommentdTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findByPostId(long postId);
}
