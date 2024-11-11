package com.rootlab.practice.contacts;

import java.util.HashMap;
import java.util.Map;

import javax.swing.text.AbstractDocument.Content;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {
	
	public Contacts contacts = new Contacts();
	
	
	@GetMapping("")
	public String contacts(Model model) {
		model.addAttribute("contacts", contacts.getContacts());
		return "contacts/contacts";
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> add(@RequestBody Map<String, Object> contact) {
		//System.out.println("받음: " + contact);
		
		String name = contact.get("name").toString();
		
		if((contacts.getContacts().containsKey(name))) {
			//중복
			//System.out.println("이미 있음 : " + name);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("중복된 이름이 있습니다.");
		}
		
		int age = Integer.valueOf(contact.get("age").toString());
		String tel = contact.get("tel").toString();
		String addr = contact.get("addr").toString();
		
		contacts.update(name, age, tel, addr);
		//contacts.printContacts();
		
		return ResponseEntity.ok("저장이 완료 되었습니다.");
	}
	
	@GetMapping("/api/update")
	@ResponseBody //json 데이터를 보낼때, @RestController에서는 생략가능
	public HashMap<String, HashMap<String, Object>> update(){
		return contacts.getContacts();
	}
	
}
