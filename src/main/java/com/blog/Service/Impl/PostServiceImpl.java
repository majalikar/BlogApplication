package com.blog.Service.Impl;

import com.blog.Entities.Post;
import com.blog.Exceptions.ResourceNotFoundException;
import com.blog.PayLoad.PostDTO;
import com.blog.PayLoad.PostResponse;
import com.blog.Repositries.BlogRepository;
import com.blog.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    BlogRepository blogRepo;

    ModelMapper mapper;

    public PostServiceImpl(BlogRepository blogRepo, ModelMapper mapper) {
        this.blogRepo = blogRepo;
        this.mapper = mapper;
    }

    @Override
    public PostDTO createPost(PostDTO postdto) {
        Post post = mapToEntity(postdto);
        Post posts = blogRepo.save(post);

        PostDTO postdtos = mapToDto(posts);
        return postdtos;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDIR) {

        Sort sort = sortDIR.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        PageRequest content = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> post = blogRepo.findAll(content);
        List<Post> posts = post.getContent();
        List<PostDTO> postdtos =  posts.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postdtos);
        postResponse.setPageNo(post.getNumber());
        postResponse.setPageSize(post.getSize());
        postResponse.setTotalPages(post.getTotalPages());
        postResponse.setTotalElements(post.getTotalElements());
        postResponse.setLast(post.isLast());

        return postResponse;

    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = blogRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found by id: "+id)
        );
        PostDTO postDTO = mapToDto(post);
        return postDTO;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post = blogRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: "+id)
        );
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        Post savedPost = blogRepo.save(post);
        PostDTO mappedToDto = mapToDto(savedPost);
        return mappedToDto;
    }

    @Override
    public void deletePost(long id) {
        Post post = blogRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Post not found with id: "+id)
        );
        blogRepo.deleteById(id);
    }

    Post mapToEntity(PostDTO postdto){
        Post post = mapper.map(postdto, Post.class);
        return post;
    }
    PostDTO mapToDto(Post posts){
        PostDTO dto = mapper.map(posts, PostDTO.class);
        return dto;
    }
}
