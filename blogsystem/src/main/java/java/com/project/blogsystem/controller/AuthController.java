package com.project.blogsystem.controller;

import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blogsystem.entity.User;
import com.project.blogsystem.exception.ApiException;
import com.project.blogsystem.payload.JwtAuthRequest;
import com.project.blogsystem.payload.UserDTO;
import com.project.blogsystem.repository.UserRepository;
import com.project.blogsystem.security.CustomUserDetailService;
import com.project.blogsystem.security.JwtAuthResponse;
import com.project.blogsystem.security.JwtTokenHelper;
import com.project.blogsystem.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ModelMapper mapper;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		System.out.println("in createtoken method : "+request.getUsername()+"   "+ request.getPassword());
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = customUserDetailService.loadUserByUsername(request.getUsername());
		System.out.println("userDetails ="+userDetails);
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.mapper.map((User) userDetails, UserDTO.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {
			System.out.println("username " +username);
			System.out.println("in authenticate "+authenticationToken);
			this.authenticationManager.authenticate(authenticationToken);
			System.out.println("done");
			

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details !!");
			throw new ApiException("Invalid username or password !!");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// register new user api

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDto) {
		UserDTO registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDTO>(registeredUser, HttpStatus.CREATED);
	}

	// get loggedin user data
	

	@GetMapping("/current-user/")
	public ResponseEntity<UserDTO> getUser(Principal principal) {
		User user = this.userRepo.findByEmail(principal.getName()).get();
		return new ResponseEntity<UserDTO>(this.mapper.map(user, UserDTO.class), HttpStatus.OK);
	}
}
