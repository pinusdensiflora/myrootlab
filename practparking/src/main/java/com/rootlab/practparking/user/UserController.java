package com.rootlab.practparking.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	
	@PostMapping("/api/signup")
	public String signup(UserEntity u) throws Exception {
		System.out.println(u);
		
		userService.save(u);
		
		
		//return "redirect:/parking/signin";
		return "redirect:/signin";
	}
	
	
	@PostMapping("/api/duplicate")
	public ResponseEntity<Boolean> duplicate(@RequestBody String username) {
		username = username.replace("\"", "");
		//User u = userService.findByUsername(username);
		//System.out.println(u.toString());
		if(userService.findByUsername(username) != null) {
			return ResponseEntity.ok(true);
		}else {
			return ResponseEntity.ok(false);
		}
	
	}
	
	
}
