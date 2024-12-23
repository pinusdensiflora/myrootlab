package com.rootlab.practparking;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/signup")
	public String signup() {
		
		return "signup";
	}
	
	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}

}
