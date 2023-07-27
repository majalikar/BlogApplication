package com.blog.Service.Impl;

import com.blog.Entities.Comment;
import com.blog.Entities.Post;
import com.blog.Exceptions.BlogApiException;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.PayLoad.CommentdTO;
import com.blog.Repositries.BlogRepository;
import com.blog.Repositries.CommentRepository;
import com.blog.Service.Commentservice;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements Commentservice {

    private CommentRepository commentRepository;
    private BlogRepository blogRepository;
    ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, BlogRepository blogRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentdTO createComment(long id, CommentdTO commentdTO) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found for id")
        );
        Comment comment = maptoEntity(commentdTO);
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    @Override
    public List<CommentdTO> getComment(long id) {
        Post post = blogRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id")
        );
        List<Comment> comments = commentRepository.findByPostId(id);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentdTO getCommentById(long postId, long id) {
        Post post = blogRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post not found for: " + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("comment not found for: " + id)
        );
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesn't match with post");
        }
        CommentdTO dto = mapToDTO(comment);
        return dto;
    }

    @Override
    public CommentdTO updateComment(long postId, long id, CommentdTO commentDto) {
        Post post = blogRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id: " + id)
        );
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesn't match with post");
        }
        comment.setId(id);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment savedComment = commentRepository.save(comment);
        return mapToDTO(savedComment);
    }

    @Override
    public void deleteComment(long postId, long id) {
        Post post = blogRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post not found with id: " + postId)
        );
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id: " + id)
        );
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment doesn't match with post");
        }
        commentRepository.deleteById(id);
    }

    Comment maptoEntity(CommentdTO commentdTO){
        Comment comment = mapper.map(commentdTO, Comment.class);
        return comment;
    }
     CommentdTO mapToDTO(Comment newComment){
         CommentdTO mapped = mapper.map(newComment, CommentdTO.class);
         return mapped;
    }
}

