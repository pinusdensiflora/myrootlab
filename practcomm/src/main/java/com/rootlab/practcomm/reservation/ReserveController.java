package com.rootlab.practcomm.reservation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReserveController {

	@GetMapping("/reserve")
	public String reserve() {
		return "reserve";
	}
	@GetMapping("/quartz")
	public String quartz() {
		return "quartz";
	}
	
	@GetMapping("/management")
	public String management(Model model) {
		model.addAttribute("reservations", "예약목록");
		
		return "management";
	}
}
