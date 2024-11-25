package com.rootlab.practice.db;

import lombok.Data;


@Data
public class Person {

	private int id;
	private String name;
	private int age;
	private String agegroup;
	private String gender;
	private int score;
	private String grade;
	private String status;

}




//CREATE TABLE person(
//		
//		id INT AU` PRI` KEY
//		name VARCHAR(255),
//		age INT,
//		gender VARCHAR(1), //남, 여
//		score INT,
//		grade VARCHAR(1), //한글자
//		status VARCHAR(255)
//		);