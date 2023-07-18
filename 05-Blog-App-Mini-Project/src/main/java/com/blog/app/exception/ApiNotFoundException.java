package com.blog.app.exception;

public class ApiNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7765847766413378505L;

	public ApiNotFoundException() {
		super();

	}

	public ApiNotFoundException(String e) { 
		super(e);
	}

}
