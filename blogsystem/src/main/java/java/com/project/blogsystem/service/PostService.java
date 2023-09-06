package com.project.blogsystem.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.blogsystem.entity.Category;
import com.project.blogsystem.entity.Post;
import com.project.blogsystem.entity.User;
import com.project.blogsystem.exception.ResourceNotFoundException;
import com.project.blogsystem.payload.PostDTO;
import com.project.blogsystem.payload.PostResponse;
import com.project.blogsystem.repository.CategoryRepository;
import com.project.blogsystem.repository.PostRepository;
import com.project.blogsystem.repository.UserRepository;


@Service
@Transactional
public class PostService implements PostServiceInterface {
	
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	@Autowired
	private ModelMapper modelMapper= new ModelMapper();



	@Override
	public PostDTO createPost(PostDTO postDTO, Integer categoryId, Integer userId) {
		// TODO Auto-generated method stub
		
		
		Optional<Category> categoryo = categoryRepository.findById(categoryId);
		Category category= categoryo.orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Optional<User> usero = userRepository.findById(userId);
		User user= usero.orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId)); 
		
		
		Post post =  modelMapper.map(postDTO, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		Post newPost = postRepository.save(post);
		
	
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		
		
		return modelMapper.map(newPost, PostDTO.class);
		
		
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, Integer postId) {
		// TODO Auto-generated method stub\
		Post post= postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("postId", "postId", postId));
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImageName(postDTO.getImageName());
		
		Post updatedPost = postRepository.save(post);
		
		return modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public PostDTO getPostById(Integer postId) {
		// TODO Auto-generated method stub
		Post post= postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("postId", "postId", postId));
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return modelMapper.map(post, PostDTO.class);

	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		
		Pageable page= PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> pagePost= postRepository.findAll(page);
		List<Post> posts=pagePost.getContent();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		List<PostDTO> postDTOs = posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDTOs);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
		
	
		
		
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post post= postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("postId", "postId", postId));
		
		postRepository.delete(post);
		
		
		
	}

	@Override
	public List<PostDTO> getPostByCatgeory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category= categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("categoryId", "category id", categoryId)); 
		List<Post> posts= postRepository.findByCategory(category);
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		return posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		// TODO Auto-generated method stub
		User user= userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("userId", "user id", userId));
		
		List<Post> posts= postRepository.findByUser(user);
		
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		return posts.stream().map(post -> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		List<Post> posts= postRepository.findByTitleContaining(keyword);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		return posts.stream().map(post->modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		
	}


}
