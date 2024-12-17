package com.rootlab.practcomm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("")
	public String main() {
		return "websearch";
	}
	
	@GetMapping("/img")
	public String img() {
		return "img";
	}



}
