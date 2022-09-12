package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostDtoV2;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import com.springboot.blog.springbootblogrestapi.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

// Can be named PostResource
@RestController
@RequestMapping()
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

//    Create blog post REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

//    Get all posts REST API
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/v1/posts")
    public PostResponse getAllPosts(
            @Valid
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

//    Get post by id REST API
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostByIdV1(@Valid @PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));

//        Get post by id REST API Version 2
    }    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/api/v2/posts/{id}")
    public ResponseEntity<PostDtoV2> getPostByIdV2(@Valid @PathVariable(name = "id") long id){
        PostDto postDto = postService.getPostById(id);
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setContent(postDto.getContent());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
//        Adding Tags
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("SpringBoot");
        tags.add("AWS");
        postDtoV2.setTags(tags);

        return ResponseEntity.ok(postDtoV2);
    }

//    Update post by id REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

//    Delete post by id REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/api/v2/posts/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam("query") String query){
        return ResponseEntity.ok(postService.searchPosts(query));
    }
}

