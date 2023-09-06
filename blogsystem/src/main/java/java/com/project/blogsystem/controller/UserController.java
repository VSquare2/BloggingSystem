package com.project.blogsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogsystem.payload.ApiResponse;
import com.project.blogsystem.payload.UserDTO;
import com.project.blogsystem.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/users")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		UserDTO createuserDTO =   userService.createUser(userDTO);
		return new ResponseEntity<>(createuserDTO,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,@PathVariable("userId") Integer userId){

			UserDTO updateUserDTO = userService.updateUser(userDTO, userId);
			return new ResponseEntity<UserDTO>(updateUserDTO,HttpStatus.OK);
	        
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId){
		
	            userService.deleteUser(userId);
	            return new ResponseEntity<ApiResponse>(new ApiResponse("Successfully deleted",true),HttpStatus.OK);
	        
				
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		
		List<UserDTO> UserDTOs = userService.getAllUsers();
		return new ResponseEntity<List<UserDTO>>(UserDTOs,HttpStatus.OK);
				
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@NotBlank @Valid
			@PathVariable("userId") Integer userId){
	
		// try {
			 UserDTO getUserDTO = userService.getUserById(userId);
			 return new ResponseEntity<UserDTO>(getUserDTO,HttpStatus.OK);
//	        }  catch (Exception e) {
//	        	
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	        	
//	        }
				
	}
	
	
}
