package com.project.blogsystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.blogsystem.entity.Comment;
import com.project.blogsystem.entity.Post;
import com.project.blogsystem.exception.ResourceNotFoundException;
import com.project.blogsystem.payload.CommentDTO;
import com.project.blogsystem.repository.CommentRepository;
import com.project.blogsystem.repository.PostRepository;


@Service
@Transactional
public class CommentService implements CommentServiceInterface{
	
	@Autowired
	private PostRepository postRepository;
	
	
	@Autowired
	private CommentRepository commentRepository;
	
	
	@Autowired
	private ModelMapper modelMapper= new ModelMapper();
	

	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
		// TODO Auto-generated method stub
		
		Post post= postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("postId", "postId", postId));
		Comment comment = modelMapper.map(commentDTO, Comment.class);
		
		comment.setPost(post);
		
		Comment savedComment = commentRepository.save(comment);
		
		return modelMapper.map(savedComment, CommentDTO.class);
		
		
	}

	@Override
	public void deleteComment(Integer commentId) {
		// TODO Auto-generated method stub
		System.out.println(commentId);
		Comment com = this.commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.commentRepository.deleteById(commentId);
		
	}

}
