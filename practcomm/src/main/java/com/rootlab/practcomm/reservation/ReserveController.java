package com.rootlab.practcomm.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rootlab.practcomm.reservation.service.ReservationService;

@Controller
public class ReserveController {

	@Autowired
	private ReservationService reservationService;
	
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
		model.addAttribute("reservations", reservationService.selectAll());//페이징이나 리디렉션 없애면 그냥 api 로 해도 되긴한데.. 일단은..
		return "management";
	}
	
	@GetMapping("/api/reservation")
	@ResponseBody
	public List<Reservation> reservation() {
		return reservationService.selectAll();
	}
}
