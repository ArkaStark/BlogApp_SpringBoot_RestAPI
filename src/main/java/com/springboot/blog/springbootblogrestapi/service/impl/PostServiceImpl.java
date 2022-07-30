package com.springboot.blog.springbootblogrestapi.service.impl;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exceptions.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

//        Convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

//        Convert entity to DTO
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
//        get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
//        get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }


    //  Convert entity to DTO
    private PostDto mapToDto(Post post){
        PostDto postResponse = new PostDto();
        postResponse.setId(post.getId());
        postResponse.setTitle((post.getTitle()));
        postResponse.setDescription((post.getDescription()));
        postResponse.setContent((post.getContent()));
        return postResponse;
    }

//    Convert DTO to entity
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
