package com.fakereddit.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String postName;
    private String url;
    private String description;
    private String username;
    private String subredditName;
    private String duration;
    private Integer voteCount;
    private Integer commentCount;
    private boolean upVote;
    private boolean downVote;
}
