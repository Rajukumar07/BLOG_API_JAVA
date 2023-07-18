package com.blog.app.payload;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostDto {

	private Integer postId;
	private String title;
	private String content;
	private String description;
	private String imageName;
	private LocalDateTime createdOn;
	private UserDto user;
	private CategoryDto categories;
	private List<CommentDto> comments;
}
