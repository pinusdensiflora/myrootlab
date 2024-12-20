package com.rootlab.practcomm.reservation.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Reservation {

	private int id;
	private String keyword;
	private String type;
	private int type_code;
	private String sort;
	
	private String cron;
	private String status;
	private int status_code;
	private LocalDateTime createDate;
	private String use_yn;
	private String jobname;
	
}
