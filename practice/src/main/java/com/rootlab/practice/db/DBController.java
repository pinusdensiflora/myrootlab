package com.rootlab.practice.db;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/db")
public class DBController {

	
	@GetMapping("")
	public String dbmain(){
		return "/db/db";
	}
}
