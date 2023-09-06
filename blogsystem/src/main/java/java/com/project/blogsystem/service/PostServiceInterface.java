package com.project.blogsystem.service;

import java.util.List;

import com.project.blogsystem.payload.PostDTO;
import com.project.blogsystem.payload.PostResponse;

public interface PostServiceInterface {

	PostDTO createPost(PostDTO postDTO , Integer categoryId, Integer userId);
	
	PostDTO updatePost(PostDTO postDTO, Integer postId);
	
	PostDTO getPostById(Integer postId);
	
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);

	void deletePost(Integer postId);
	
	List<PostDTO> getPostByCatgeory(Integer categoryId);
	
	List<PostDTO> getPostByUser(Integer userId);
	
	List<PostDTO> searchPosts(String keyword);
	
	
}
