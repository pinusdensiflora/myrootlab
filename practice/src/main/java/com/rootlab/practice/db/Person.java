package com.rootlab.practice.db;

import lombok.Builder;
import lombok.Data;

//mybatis는 기본생성자를 써서 기본생성자를 못쓰는 Builder 와 충돌이 나야할 건데,, 이유모르게 잘 돌아감... mariaDB 세팅에 mysql을 돌리고 있어서 그런가
//아무튼 추가 어노테이션을 붙여 기본생성자 삭제를 막거나 해야함
//그냥 빌더를 안쓰는 것이 일반적이긴 함

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
