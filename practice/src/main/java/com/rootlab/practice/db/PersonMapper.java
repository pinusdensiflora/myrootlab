package com.rootlab.practice.db;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PersonMapper {
	void save(Person person);
	List<Person> findAll();
	List<Person> findAllOrderByScoreAge();
	
	Person findById(int id);
}
