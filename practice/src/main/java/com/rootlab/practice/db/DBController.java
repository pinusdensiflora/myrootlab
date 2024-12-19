package com.rootlab.practice.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/db")
@RequiredArgsConstructor
public class DBController {
	
	private final PersonService personService;

	@GetMapping("")
	public String dbmain(){
		return "/db/db";
	}
	
	@GetMapping("/api/list")
	@ResponseBody
	public List<Person> getlist(){
		
		List<Person> list = new ArrayList<>();
		/*
		 * Person p1 = Person.builder().age(10).name("우우").build(); Person p2 =
		 * Person.builder().age(15).name("우수").build(); list.add(p1); list.add(p2);
		 */
		
		return personService.findAllOrderByScoreAge();
		

		//System.out.println(personService.findById(10).getName());
		//return list;
		
	}
	
	@PostMapping("/api")
	public ResponseEntity<String> api(@RequestBody Map<String, String> person) {
		//System.out.println(person.get("name"));
		Person p = Person.builder()
							.name(person.get("name"))
							.age(Integer.parseInt(person.get("age")))
							.gender(person.get("gender"))
							.score(Integer.parseInt(person.get("score")))
							.status(person.get("status"))
							.build();
		
		System.out.println(p.toString());
		personService.save(p);
		
		
		return ResponseEntity.ok("");
	}
	
	
}
