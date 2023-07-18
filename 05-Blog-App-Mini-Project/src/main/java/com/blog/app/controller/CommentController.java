package com.blog.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.exception.ApiResponse;
import com.blog.app.payload.CommentDto;
import com.blog.app.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> doComment(@PathVariable Integer postId, @RequestBody CommentDto commentDto) {

		CommentDto createComment = commentService.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);

	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable Integer commentId) {
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(new ApiResponse("comment is deleted..", true), HttpStatus.OK);
	}
}
