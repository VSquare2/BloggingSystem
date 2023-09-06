package com.project.blogsystem.payload;

import lombok.Data;

@Data
public class ApiResponse {
	
	private String msg;
	private boolean status;
	public ApiResponse(String msg, boolean status) {
		super();
		this.msg = msg;
		this.status = status;
	}
	
}
