package com.blog.Controller;

import com.blog.PayLoad.CommentdTO;
import com.blog.Service.Commentservice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/")
@RestController
public class CommentController {
    private Commentservice commentservice;

    public CommentController(Commentservice commentservice) {
        this.commentservice = commentservice;
    }
    //http://localhost:8080/api/posts/id/comments
    @PostMapping("/posts/{PostId}/comments")
    public ResponseEntity<CommentdTO> createComment(@PathVariable("PostId")Long id, @RequestBody CommentdTO commentdTO){
        CommentdTO newdto = commentservice.createComment(id, commentdTO);
        return new ResponseEntity<>(newdto, HttpStatus.CREATED );
    }
    @GetMapping("/posts/{PostId}/comments")
    public List<CommentdTO> getCommentBypostId(@PathVariable("PostId")long id){
        return commentservice.getComment(id);
    }
    //http://localhost:8080/api/posts/postId/comments/id
    @GetMapping("/posts/{PostId}/comments/{id}")
    public ResponseEntity<CommentdTO> getCommentById(@PathVariable("PostId") long postId, @PathVariable("id") long id){
        CommentdTO commentById = commentservice.getCommentById(postId, id);
        return new ResponseEntity<>(commentById, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/postId/comments/id
    @PutMapping("/posts/{PostId}/comments/{id}")
    public ResponseEntity<CommentdTO> updateComment(@PathVariable("PostId")long postId, @PathVariable("id")long id, @RequestBody CommentdTO commentDto){
        CommentdTO dto = commentservice.updateComment(postId, id, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/postId/comments/id
    @DeleteMapping("/posts/{PostId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("PostId")long postId, @PathVariable("id")long id){
        commentservice.deleteComment(postId, id);
        return new ResponseEntity<>("Comment is deleted", HttpStatus.OK);
    }
}


