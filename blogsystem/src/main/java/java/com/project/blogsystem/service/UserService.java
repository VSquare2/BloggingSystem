package com.project.blogsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blogsystem.config.AppConstants;
import com.project.blogsystem.entity.Role;
import com.project.blogsystem.entity.User;
import com.project.blogsystem.exception.ResourceNotFoundException;
import com.project.blogsystem.payload.UserDTO;
import com.project.blogsystem.repository.RoleRepository;
import com.project.blogsystem.repository.UserRepository;


@Service
public class UserService implements UserServiceInterface{
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = dtoToUser(userDTO);
		User createdUser=userRepository.save(user);
		return userToDTO(createdUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		// TODO Auto-generated method stub
		
		Optional<User> usero = userRepository.findById(userId);
		User user= usero.orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		user.setUname(userDTO.getUname());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		userRepository.save(user);	
		return userToDTO(user);		
		
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		
		Optional<User> usero = userRepository.findById(userId);
		User user= usero.orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		userRepository.deleteById(userId);
		
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		// TODO Auto-generated method stub
		
		Optional<User> usero = userRepository.findById(userId);
		User user= usero.orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		UserDTO userDTO1 = userToDTO(user);
		return userDTO1;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		
		List<User> users =userRepository.findAll();
		List<UserDTO> userDTOs = users.stream().map(user-> userToDTO(user)).collect(Collectors.toList());
		
		return userDTOs;
	}
	
	@Override
	public UserDTO registerNewUser(UserDTO userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(user.getPassword());

		// roles
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepository.save(user);

		return this.modelMapper.map(newUser, UserDTO.class);
	}
	
	private User dtoToUser(UserDTO userDTO) {
		User user=new User();
		modelMapper.map(userDTO, user);
		
//		user.setUid(userDTO.getUid());
//		user.setUname(userDTO.getUname());
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
//		user.setAbout(userDTO.getAbout());
		return user;
	}
	
	private UserDTO userToDTO(User user) {
		UserDTO userDTO=new UserDTO();
		modelMapper.map(user, userDTO);
		
//		userDTO.setUid(user.getUid());
//		userDTO.setUname(user.getUname());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAbout(user.getAbout());
		return userDTO;
	}

}
