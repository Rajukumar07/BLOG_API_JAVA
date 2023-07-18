package com.blog.app.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	private int pageSize;
	private int pageNo;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
}
