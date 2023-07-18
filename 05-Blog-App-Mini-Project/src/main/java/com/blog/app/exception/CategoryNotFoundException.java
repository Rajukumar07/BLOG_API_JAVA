package com.blog.app.exception;

public class CategoryNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 156736330852402706L;

	String resourceName;
	String fieldName;
	long fieldValue;

	public CategoryNotFoundException() {

	}

	public CategoryNotFoundException(String resourceName, String fieldName, long fieldValue) {

		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
