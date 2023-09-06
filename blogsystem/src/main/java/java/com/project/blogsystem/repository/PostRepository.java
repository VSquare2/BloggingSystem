package com.project.blogsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogsystem.entity.Category;
import com.project.blogsystem.entity.Post;
import com.project.blogsystem.entity.User;



public interface PostRepository  extends JpaRepository<Post, Integer> {

	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);
}
