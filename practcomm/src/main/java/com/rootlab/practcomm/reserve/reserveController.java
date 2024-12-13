package com.rootlab.practcomm.reserve;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class reserveController {

	@GetMapping("/reserve")
	public String reserve() {
		return "reserve";
	}
	@GetMapping("/quartz")
	public String quartz() {
		return "quartz";
	}
}
