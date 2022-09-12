package com.springboot.blog.springbootblogrestapi.repository;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

//    JPQL Query
    @Query("SELECT p FROM Post p WHERE " +
    "p.title LIKE CONCAT('%', :query, '%')" +
    "Or p.description LIKE CONCAT('%', :query, '%')")
    List<Post> searchPosts(String query);

//    Native SQL Query
    @Query(value = "SELECT * FROM posts p WHERE " +
            "p.title LIKE CONCAT('%', :query, '%')" +
            "Or p.description LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<Post> searchPostsSQL(String query);
}
