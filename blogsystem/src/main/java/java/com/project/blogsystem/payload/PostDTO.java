package com.project.blogsystem.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class PostDTO {
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDTO categoryDTO;

	private UserDTO userDTO;
	
	private Set<CommentDTO> comments=new HashSet<>();

	

}
