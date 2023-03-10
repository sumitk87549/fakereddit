package com.fakereddit.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private Long postId;
    private String subredditName;
    private String postName;
    private String url;
    private String description;
    private String username;
}
