package com.blog.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.exception.ApiResponse;
import com.blog.app.payload.CategoryDto;
import com.blog.app.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

		CategoryDto category = categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
	}

	@PutMapping("/{cateId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("cateId") Integer categoryId) {

		CategoryDto upadted = categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(upadted, HttpStatus.OK);
	}

	@DeleteMapping("/{cateId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("cateId") Integer categoryId) {

		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponse("Category is deleted successfully", true), HttpStatus.OK);
	}

	@GetMapping("/{cateId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable("cateId") Integer categoryId) {

		CategoryDto category = categoryService.getCategory(categoryId);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory() {

		List<CategoryDto> allCategory = categoryService.getAllCategory();
		return new ResponseEntity<>(allCategory, HttpStatus.OK);
	}
}
