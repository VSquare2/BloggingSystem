package com.project.blogsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blogsystem.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
