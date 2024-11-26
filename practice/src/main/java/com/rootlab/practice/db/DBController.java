package com.rootlab.practice.db;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/db")
public class DBController {
	

	@GetMapping("")
	public String dbmain(){
		return "/db/db";
	}
	
//	@GetMapping("/api")
//	public List<Person> api(){
//		//return 
//	}
	
	@PostMapping("/api")
	public ResponseEntity<String> api(@RequestBody Map<String, String> person) {
		System.out.println(person.toString());
		
		return ResponseEntity.ok("");
	}
	
	
}
