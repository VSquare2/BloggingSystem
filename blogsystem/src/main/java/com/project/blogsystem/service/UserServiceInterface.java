package com.project.blogsystem.service;

import java.util.List;

import com.project.blogsystem.payload.UserDTO;

public interface UserServiceInterface {

	
	UserDTO createUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO, Integer userId);
	void deleteUser(Integer userId);
	UserDTO getUserById(Integer userId);
	List<UserDTO> getAllUsers();
	
}
