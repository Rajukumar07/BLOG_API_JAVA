package com.blog.app.service;

import java.util.List;

import com.blog.app.payload.PostDto;
import com.blog.app.payload.PostResponse;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get post by id
	PostDto getPost(Integer postId);

	// get All post
	PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

	// get post by user
	List<PostDto> getPostByUser(Integer userId);

	// get post by category
	List<PostDto> getPostByCategory(Integer categoryId);

	List<PostDto> searchPosts(String keyword);
}
