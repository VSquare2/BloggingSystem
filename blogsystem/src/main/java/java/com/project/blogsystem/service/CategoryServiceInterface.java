package com.project.blogsystem.service;

import java.util.List;

import com.project.blogsystem.payload.CategoryDTO;
import com.project.blogsystem.payload.UserDTO;

public interface CategoryServiceInterface {

	

	CategoryDTO createCategory(CategoryDTO categoryDTO);
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
	void deleteCategory(Integer categoryId);
	CategoryDTO getCategoryById(Integer categoryId);
	List<CategoryDTO> getAllCategory();
}
