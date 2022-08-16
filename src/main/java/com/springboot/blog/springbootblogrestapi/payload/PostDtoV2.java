package com.springboot.blog.springbootblogrestapi.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
public class PostDtoV2 {
    private long id;

    //    Title should not be empty or null and have at least 2 characters
    @NotEmpty
    @Size(min=2, message = "Post title should have at least 2 characters")
    private String title;

    //    Description should not be empty and have at least 10 characters
    @NotEmpty
    @Size(min=10, message = "Post description should have at least 10 characters")
    private String description;

    //    Should not be empty
    @NotEmpty
    private String content;

    private List<String> tags;

    private Set<CommentDto> comments;
}
