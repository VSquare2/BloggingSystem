package com.project.blogsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blogsystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

}
