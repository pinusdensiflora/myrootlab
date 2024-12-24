package com.rootlab.practparking.user;

import java.time.LocalDateTime;

import lombok.Data;


//import org.springframework.security.core.userdetails.User; 와 이름이 겹치지 않게 UserEntity 사용
@Data
public class UserEntity {

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
