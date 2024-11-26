package com.rootlab.practice.db;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {
	
	private final PersonMapper personMapper;
	
	public void save(Person person) {
		System.out.println("서비스"+person.toString());
		personMapper.save(person);
	}
	public List<Person> findAllOrderByScoreAge(){
		
		return personMapper.findAllOrderByScoreAge();
	}
	public List<Person> findAll(){
		return personMapper.findAll();
	}

	public Person findById(int id) {
		return personMapper.findById(id);
	}
}
