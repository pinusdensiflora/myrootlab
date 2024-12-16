package com.rootlab.practcomm.reservation;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Reservation {

	private int id;
	private String keyword;
	private String type;
	private String sort;
	private String cron;
	private String status;
	private LocalDateTime createDate;
	private String use_yn;
	
}
