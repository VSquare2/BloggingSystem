package com.project.blogsystem.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.blogsystem.config.AppConstants;
import com.project.blogsystem.payload.ApiResponse;
import com.project.blogsystem.payload.PostDTO;
import com.project.blogsystem.payload.PostResponse;
import com.project.blogsystem.service.FileService;
import com.project.blogsystem.service.PostService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api")
public class PostController {
	
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO,@PathVariable("userId") Integer userId,
			@PathVariable("categoryId") Integer categoryId){
		PostDTO createCategoryDTO =   postService.createPost(postDTO, categoryId, userId);
		return new ResponseEntity<>(createCategoryDTO,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable("userId") Integer userId){
		List<PostDTO> posts = postService.getPostByUser(userId);
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId){
		List<PostDTO> posts = postService.getPostByCatgeory(categoryId);
		return new ResponseEntity<>(posts,HttpStatus.OK);
	}
	
	
	@PutMapping("/{postId}")
	 public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,@PathVariable("postId") Integer postId){
		
		PostDTO updateCategoryDTO = postService.updatePost(postDTO, postId);
			return new ResponseEntity<PostDTO>(updateCategoryDTO,HttpStatus.OK);
	        
				
	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
		
		postService.deletePost(postId);
			 
	            return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted",true),HttpStatus.OK);
	         
				
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR, required=false) String sortDir
			){
			
		PostResponse PostDTOs = postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(PostDTOs,HttpStatus.OK);
				
	}

	@GetMapping("/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable("postId") Integer postId){
	
		
		 	PostDTO getPostDTO = postService.getPostById(postId);
				
				return new ResponseEntity<PostDTO>(getPostDTO,HttpStatus.OK);
	       
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keywords") String keywords){
		List<PostDTO> result=postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDTO>>(result,HttpStatus.OK);
	}
	
	// post image upload

		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId) throws IOException {

			PostDTO postDto = postService.getPostById(postId);
			
			String fileName = fileService.uploadImage(path, image);
			postDto.setImageName(fileName);
			PostDTO updatePost = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

		}
		

}
