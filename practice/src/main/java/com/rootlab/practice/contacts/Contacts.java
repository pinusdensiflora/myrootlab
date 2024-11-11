package com.rootlab.practice.contacts;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Contacts {
	

	private HashMap<String, HashMap<String, Object>> realContacts;
	
    public Contacts() {
        this.realContacts = new HashMap<>();
        HashMap<String, Object> contact1 = new HashMap<>();
        contact1.put("나이", 25);
        contact1.put("번호", "01012341234");
        contact1.put("주소", "가가가");
    
        HashMap<String, Object> contact2 = new HashMap<>();
        contact2.put("나이", 15);
        contact2.put("번호", "01099999999");
        contact2.put("주소", "나나나");
        
        realContacts.put("미영", contact1);
        realContacts.put("영수", contact2);
    }
	
	@Bean
	public HashMap<String, HashMap<String, Object>> getContacts() {
		return this.realContacts;
	}
	
	public void update(String name, int age, String tel, String addr) {
		
		HashMap<String, Object> subHash = new HashMap<>();
		
		subHash.put("나이", age);
		subHash.put("번호", tel);
		subHash.put("주소", addr);
		
		this.realContacts.put(name, subHash);
		
		System.out.println(this.realContacts.toString());
		
	}
	
	public void printContacts() {
		System.out.println("현재 주소록: " + this.realContacts.toString());
	}

	
}
