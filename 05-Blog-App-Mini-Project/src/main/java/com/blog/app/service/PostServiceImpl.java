package com.blog.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.app.config.AppConstants;
import com.blog.app.entity.Categories;
import com.blog.app.entity.Post;
import com.blog.app.entity.User;
import com.blog.app.exception.CategoryNotFoundException;
import com.blog.app.exception.ResourceNotFoundException;
import com.blog.app.exception.UserNotFoundException;
import com.blog.app.payload.PostDto;
import com.blog.app.payload.PostResponse;
import com.blog.app.repositories.CategoryRepo;
import com.blog.app.repositories.PostRepo;
import com.blog.app.repositories.UserRepo;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		Categories categories = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category", "Category Id", categoryId));
		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "User Id", userId));

		// converting post dto to post entity object
		// Post post = dtoToPost(postDto);
		Post post = mapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setCategory(categories);
		post.setUser(user);

		Post save = postRepo.save(post);
		// return postToDto(save);
		return mapper.map(save, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
//		mapper.map(postDto, posts);
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post update = postRepo.save(post);
		return mapper.map(update, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post posts = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

		postRepo.delete(posts);

	}

	@Override
	public PostDto getPost(Integer postId) {
		Post posts = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

		return mapper.map(posts, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = null;
		if (sortDir.equalsIgnoreCase(AppConstants.SORT_DIR)) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}

		Pageable p = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> pagePost = postRepo.findAll(p);
		List<Post> content = pagePost.getContent();
		List<PostDto> postDto = content.stream().map(post -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		// return post response
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDto);
		postResponse.setPageNo(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User", "User Id", userId));
		List<Post> posts = postRepo.findByUser(user);
		List<PostDto> postDto = posts.stream().map(post -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDto;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {

		Categories category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts = postRepo.findByCategory(category);
		List<PostDto> postDto = posts.stream().map((post) -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> titleContaining = postRepo.findByTitleContaining(keyword);
		List<PostDto> postDto = titleContaining.stream().map(title -> mapper.map(title, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

//	// Method for convert from category entity to category binding object
//
//	private PostDto postToDto(Post post) {
//
//		PostDto dto = new PostDto();
//		BeanUtils.copyProperties(post, dto);
//		return dto;
//	}
//
//	// Method for convert from binding object to entity object of category
//	private Post dtoToPost(PostDto dto) {
//
//		Post post = new Post();
//		BeanUtils.copyProperties(dto, post);
//		return post;
//	}

}
