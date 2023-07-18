package com.blog.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.app.config.AppConstants;
import com.blog.app.exception.ApiResponse;
import com.blog.app.payload.PostDto;
import com.blog.app.payload.PostResponse;
import com.blog.app.service.FileService;
import com.blog.app.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;

	// create

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto createPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {

		PostDto updatePost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	// delete post
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("deleted successfully", true), HttpStatus.OK);
	}

	// get post by user

	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {

		List<PostDto> postByUser = postService.getPostByUser(userId);
		return new ResponseEntity<>(postByUser, HttpStatus.OK);
	}

	// get post by category

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {

		List<PostDto> postByUser = postService.getPostByCategory(categoryId);
		return new ResponseEntity<>(postByUser, HttpStatus.OK);
	}

	// get post by post id

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {

		PostDto post = postService.getPost(postId);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	// get all post

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		PostResponse postResponse = postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> searchPosts = postService.searchPosts(keywords);
		return new ResponseEntity<>(searchPosts, HttpStatus.OK);
	}

	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@PathVariable Integer postId, @RequestParam("image") MultipartFile image)
			throws IOException {
		PostDto postDto = postService.getPost(postId);
		String imageName = fileService.uploadImage(path, image);
		postDto.setImageName(imageName);
		postService.updatePost(postDto, postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	@GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
