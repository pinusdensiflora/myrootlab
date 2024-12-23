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
	
	
	@PostMapping("/signup")
	public String signup(User u) throws Exception {
		System.out.println(u);
		
		userService.save(u);
		
		
		//return "redirect:/parking/signin";
		return "redirect:/signin";
	}
	
	
	@GetMapping("/duplicate")
	public boolean duplicate(@RequestBody String username) {
		if(userService.findByUsername(username) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
