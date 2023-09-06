package com.project.blogsystem;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.project.blogsystem.config.AppConstants;
import com.project.blogsystem.entity.Role;
import com.project.blogsystem.repository.RoleRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
public class BlogsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogsystemApplication.class, args);
	}
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public void run(String... args) throws Exception {

	//	System.out.println(this.passwordEncoder.encode("xyz"));

		try {

			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role, role1);

			List<Role> result = this.roleRepository.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}
