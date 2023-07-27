package com.blog.Controller;

import com.blog.PayLoad.PostDTO;
import com.blog.PayLoad.PostResponse;
import com.blog.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

 private PostService postService;

 public PostController(PostService postService) {
  this.postService = postService;
 }

 //http://localhost:8080/api/posts
 @PreAuthorize("hasRole('ADMIN')")
 @PostMapping
 public ResponseEntity<?> createPost(@Valid @RequestBody PostDTO postdto, BindingResult result){
  if (result.hasErrors()) {
   List<String> errorMessages = new ArrayList<>();
   for (FieldError error : result.getFieldErrors()) {
    errorMessages.add(error.getDefaultMessage());
   }
   return new ResponseEntity<>(errorMessages, HttpStatus.INTERNAL_SERVER_ERROR);
  }


  PostDTO dto = postService.createPost(postdto);
  return new ResponseEntity<>(dto, HttpStatus.CREATED);
 }
 //http://localhost:8080/api/posts?pageNo=0&pageSize=5
 @GetMapping
 public PostResponse getAllPosts(
         @RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
         @RequestParam (value = "pageSize", defaultValue = "5", required = false) int pageSize,
         @RequestParam (value = "sortBy", defaultValue = "id", required = false) String sortBy,
         @RequestParam (value = "sortDIR", defaultValue = "asc", required = false) String sortDIR
 ){
  PostResponse postResponse =  postService.getAllPosts(pageNo, pageSize, sortBy, sortDIR);
  return postResponse;
 }
 //http://localhost:8080/api/posts/{id}
 @GetMapping("/{id}")
 public ResponseEntity<PostDTO> getPostById(@PathVariable("id")long id){
  PostDTO Dto = postService.getPostById(id);
  return new ResponseEntity<>(Dto, HttpStatus.OK);
 }
 //http://localhost:8080/api/posts/{id}
 @PreAuthorize("hasRole('ADMIN')")
 @PutMapping("/{id}")
 public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable("id")long id){
  PostDTO dto =  postService.updatePost(postDTO, id);
  return new ResponseEntity<>(dto, HttpStatus.OK);
 }
 //http://localhost:8080/api/posts/{id}
 @PreAuthorize("hasRole('ADMIN')")
 @DeleteMapping("/{id}")
 public ResponseEntity<String> deletePost(@PathVariable("id")long id){
  postService.deletePost(id);
  return new ResponseEntity<>("Post has been deleted",HttpStatus.OK);
 }
 }

