package com.blog.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entity.Categories;

public interface CategoryRepo extends JpaRepository<Categories, Integer> {

}
