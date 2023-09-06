package com.project.blogsystem.service;

import com.project.blogsystem.payload.CommentDTO;

public interface CommentServiceInterface {

	CommentDTO createComment(CommentDTO dto, Integer postId);
	
	void deleteComment(Integer commentId);
}
