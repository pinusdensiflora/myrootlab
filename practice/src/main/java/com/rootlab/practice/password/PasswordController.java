package com.rootlab.practice.password;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/password")
public class PasswordController {

	@GetMapping("")
	public String password() {
		return "/password/password";
	}
	@GetMapping("/mk")
	public String passwordmk() {
		return "/password/passwordmk";
	}
}
