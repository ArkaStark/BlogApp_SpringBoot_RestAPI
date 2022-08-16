package com.springboot.blog.springbootblogrestapi.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {
    private Long id;

//     Name should not be empty
    @NotEmpty(message = "Name should not be empty")
    private String name;


//  Email should not be empty and have proper format
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Incorrect format for email")
    private String email;


//    Comment body should not be empty and have at least 5 characters
    @NotEmpty(message = "Comment body should not be empty")
    @Size(min = 5, message = "Comment should be at least 5 characters")
    private String body;
}
