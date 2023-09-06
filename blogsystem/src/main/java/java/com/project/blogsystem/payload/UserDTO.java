package com.project.blogsystem.payload;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

	
	private int uid;
	
	@NotEmpty
	private String uname;
	
	@Email(message="email is not valid")
	private String email;
	
	@NotNull
	@Size(min=3, message="length of the password is small")
	private String password;
	
	@NotNull
	private String about;
	
	private Set<RoleDTO> roles = new HashSet<>();
}
