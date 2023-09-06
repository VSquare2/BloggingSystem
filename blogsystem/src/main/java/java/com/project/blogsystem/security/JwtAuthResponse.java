package com.project.blogsystem.security;

import com.project.blogsystem.payload.UserDTO;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	
	private UserDTO user;
}
