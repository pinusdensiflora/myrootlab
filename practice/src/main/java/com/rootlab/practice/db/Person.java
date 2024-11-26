package com.rootlab.practice.db;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
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



/*
CREATE TABLE person(
		
		id INT AU` PRI` KEY,
		name VARCHAR(255),
		age INT,
		agegroup VARCHAR(15),
		gender VARCHAR(1), //남, 여
		score INT,
		grade VARCHAR(1), //한글자
		status VARCHAR(255)
		);*/

/*
 * 트리거 
DELIMITER //

CREATE TRIGGER set_person
BEFORE INSERT ON person
FOR EACH ROW
BEGIN
   IF NEW.score >= 90 THEN
   	SET NEW.grade = 'A';
   ELSEIF NEW.score >= 80 THEN
   	SET NEW.grade = 'B';
   ELSEIF NEW.score >= 70 THEN
   	SET NEW.grade = 'C';
   ELSE
   	SET NEW.grade = 'D';
   
   END IF;
    
END; //

DELIMITER ;*/


/*
DELIMITER //

CREATE TRIGGER set_agegroup
BEFORE INSERT ON person
FOR EACH ROW
BEGIN
   IF NEW.age >= 60 THEN
   	SET NEW.agegroup = '60대이상';
   ELSEIF NEW.age >= 50 THEN
   	SET NEW.agegroup = '50대';
   ELSEIF NEW.age >= 40 THEN
   	SET NEW.agegroup = '40대';
   ELSEIF NEW.age >= 30 THEN
   	SET NEW.agegroup = '30대';
   ELSEIF NEW.age >= 20 THEN
   	SET NEW.agegroup = '20대';
   ELSEIF NEW.age >= 10 THEN
   	SET NEW.agegroup = '10대';
   ELSE
   	SET NEW.agegroup = '10대 미만';
   
   END IF;
    
END; //

DELIMITER ;*/
/*
SELECT NAME FROM filemeta ORDER BY createtime DESC;
SELECT NAME FROM filemeta ORDER BY name DESC;
SELECT NAME,size FROM filemeta ORDER BY createtime, NAME DESC;
*/
