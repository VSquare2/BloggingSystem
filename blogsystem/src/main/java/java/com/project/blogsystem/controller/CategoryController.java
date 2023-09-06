package com.project.blogsystem.controller;

import java.util.List;

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

import com.project.blogsystem.payload.ApiResponse;
import com.project.blogsystem.payload.CategoryDTO;
import com.project.blogsystem.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createUser(@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO createCategoryDTO =   categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(createCategoryDTO,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	 public ResponseEntity<CategoryDTO> updateUser(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable("categoryId") Integer categoryId){
		
			CategoryDTO updateCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
			return new ResponseEntity<CategoryDTO>(updateCategoryDTO,HttpStatus.OK);
	        
				
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("categoryId") Integer categoryId){
		
		 
			 categoryService.deleteCategory(categoryId);
	            return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted",true),HttpStatus.OK);
	         
				
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllUsers(){
		
		List<CategoryDTO> CategoryDTOs = categoryService.getAllCategory();
		return new ResponseEntity<List<CategoryDTO>>(CategoryDTOs,HttpStatus.OK);
				
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getUserById(@PathVariable("categoryId") Integer categoryId){
	
		
			 CategoryDTO getCategoryDTO = categoryService.getCategoryById(categoryId);
				
				return new ResponseEntity<CategoryDTO>(getCategoryDTO,HttpStatus.OK);
	        
	}
}
