package com.project.blogsystem.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		//1 get token
		//token will be sent in header in authorisation key and 
		//token will be starting from Bearer like this (Bearer gyiwqe2441)
		System.out.println("in request"+request.getHeaderNames());
		String requestToken=request.getHeader("Authorization");
		
		String userName= null;
		String token=null;
				
				
		if(requestToken!=null && requestToken.startsWith("Bearer ")) {
			
			token= requestToken.substring(7); // 7 for bearer
			System.out.println("token in first if"+token);
			try {
			userName=jwtTokenHelper.getUsernameFromToken(token);
			System.out.println("inside if try username : "+ userName);
			}
			catch(IllegalArgumentException e) {
				System.out.println("unable to get jwt token");
			}
			catch (ExpiredJwtException e) {
				// TODO: handle exception
				System.out.println("jwt token has expired");
				
			}
			catch (MalformedJwtException e) {
				// TODO: handle exception
				System.out.println("invalid jwt token");
			}
		}
		else {
			System.out.println("Jwt token doesnt start with bearer");
		}
		
		//now we have got the token, we need to validate the token
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			
			
			UserDetails userDetails  = customUserDetailService.loadUserByUsername(userName);
			System.out.println("in if userdetails : "+userDetails);
			if(jwtTokenHelper.validateToken(token, userDetails)) {
				//token validated do the authentication
				System.out.println("in if in if : "+token);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			}
//			else {
//				System.out.println("invalid token");
//			}
		}
		else {
			System.out.println("username is null or context is not null");	
		}
		filterChain.doFilter(request, response);
	}
	
	
	
	

}
