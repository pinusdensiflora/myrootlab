package com.rootlab.practice.date;

import org.checkerframework.common.reflection.qual.GetClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/date")
public class DateController {

	@GetMapping("")
	public String date() {
		return "date/date";
	}
	
}
