package com.project.blogsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogsystem.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
