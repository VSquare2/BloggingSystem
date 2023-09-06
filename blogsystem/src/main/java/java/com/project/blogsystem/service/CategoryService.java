package com.project.blogsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blogsystem.entity.Category;
import com.project.blogsystem.exception.ResourceNotFoundException;
import com.project.blogsystem.payload.CategoryDTO;
import com.project.blogsystem.repository.CategoryRepository;


@Service
public class CategoryService implements CategoryServiceInterface{

	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		Category category = new Category();
		modelMapper.map(categoryDTO, category);
		Category createdCategory = categoryRepository.save(category);
		return modelMapper.map(createdCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
		// TODO Auto-generated method stub
		
		Optional<Category> categoryo = categoryRepository.findById(categoryId);
		Category category= categoryo.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCategoryDesc(categoryDTO.getCategoryDesc());
		Category updatedCategory = categoryRepository.save(category);	
		return modelMapper.map(updatedCategory, CategoryDTO.class);
	
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category  categoryo = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		categoryRepository.delete(categoryo);
		
		
	}

	@Override
	public CategoryDTO getCategoryById(Integer categoryId) {
		// TODO Auto-generated method stub
		Optional<Category> categoryo = categoryRepository.findById(categoryId);
		Category category= categoryo.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		return modelMapper.map(category, CategoryDTO.class);
		
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		// TODO Auto-generated method stub
		List<Category> category =categoryRepository.findAll();
		List<CategoryDTO> CategoryDTOs = category.stream().map(c-> modelMapper.map(c, CategoryDTO.class)).collect(Collectors.toList());
		
		return CategoryDTOs;
	}

}
