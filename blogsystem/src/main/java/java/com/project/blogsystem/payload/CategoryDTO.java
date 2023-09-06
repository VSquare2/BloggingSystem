package com.project.blogsystem.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {

	
	private Integer categoryId;
	@NotBlank
	private String categoryTitle;
	private String categoryDesc;
}
