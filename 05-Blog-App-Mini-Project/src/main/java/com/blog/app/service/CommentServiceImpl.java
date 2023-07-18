package com.blog.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entity.Comment;
import com.blog.app.entity.Post;
import com.blog.app.exception.ResourceNotFoundException;
import com.blog.app.payload.CommentDto;
import com.blog.app.repositories.CommentRepo;
import com.blog.app.repositories.PostRepo;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {

		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		Comment comment = mapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment commented = commentRepo.save(comment);
		return mapper.map(commented, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));
		commentRepo.delete(comment);

	}

}
