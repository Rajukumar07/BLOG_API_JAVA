package com.blog.app.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobleExceptionHandlerApp {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorInfo> userNFE(UserNotFoundException e) {

		String message = e.getMessage();
		ErrorInfo errorInfo = new ErrorInfo(message, false, LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ApiNotFoundException.class)
	public ResponseEntity<ApiResponse> apiHandleException(ApiNotFoundException e) {

		String message = e.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException e) {

		Map<String, String> res = new HashMap<>();

		e.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			res.put(fieldName, message);
		});

		return new ResponseEntity<Map<String, String>>(res, HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErrorInfo> exceptionHandler(Exception e) {
//
//		String message = e.getMessage();
//		ErrorInfo errorInfo = new ErrorInfo(message, false, LocalDateTime.now());
//		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

}
