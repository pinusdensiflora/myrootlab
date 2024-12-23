package com.rootlab.practparking.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class User {

	private int id;

	private String username; 
	private String password;
	private boolean enabled;
	
	private int role_code;
	private String role;
	
	private String name;
	private String tel;
	private String email;
	
	private LocalDateTime createtime;
	
}
