package com.blog.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.entity.Categories;
import com.blog.app.entity.Post;
import com.blog.app.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Categories category);

	List<Post> findByTitleContaining(String title);

}
