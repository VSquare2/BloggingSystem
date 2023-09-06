package com.project.blogsystem.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{

	String resourceName;
	String field;
	long val;
	public ResourceNotFoundException(String resourceName, String field, long val) {
		super(resourceName+" does not exist");
		this.resourceName = resourceName;
		this.field = field;
		this.val = val;
	}
	
}
