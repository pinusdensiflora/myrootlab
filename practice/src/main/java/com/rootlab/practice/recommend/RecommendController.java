package com.rootlab.practice.recommend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/recommend")
public class RecommendController {

	
	@GetMapping("")
	public String recommend() {
		return "recommend/recommend";
	}
	
}
