package com.blog.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entity.Categories;
import com.blog.app.exception.CategoryNotFoundException;
import com.blog.app.payload.CategoryDto;
import com.blog.app.repositories.CategoryRepo;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Categories category = dtoToCategory(categoryDto);
		Categories save = categoryRepo.save(category);
		return categoryToDto(save);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {

		Categories cat = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category", "Category Id", categoryId));
		// setting the new data of category for update
		BeanUtils.copyProperties(categoryDto, cat);
		Categories updated = categoryRepo.save(cat);
		return categoryToDto(updated);

	}

	@Override
	public void deleteCategory(Integer categoryId) {

		Categories cat = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category", "Category Id", categoryId));

		categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Categories cat = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException("Category", "Category Id", categoryId));

		return categoryToDto(cat);
	}

	@Override
	public List<CategoryDto> getAllCategory() {

		List<Categories> findAll = categoryRepo.findAll();
		List<CategoryDto> cotegories = findAll.stream().map(categories -> categoryToDto(categories))
				.collect(Collectors.toList());

		return cotegories;
	}

	// Method for convert from category entity to category binding object

	private CategoryDto categoryToDto(Categories category) {

		CategoryDto dto = new CategoryDto();
		BeanUtils.copyProperties(category, dto);
		return dto;
	}

	// Method for convert from binding object to entity object of category
	private Categories dtoToCategory(CategoryDto dto) {

		Categories category = new Categories();
		BeanUtils.copyProperties(dto, category);
		return category;
	}

}
