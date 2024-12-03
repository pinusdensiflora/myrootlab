package com.rootlab.practcomm.schedule;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ScheduleDTO {
	private int id;
	private String keyword;
	private String type;
	private String sort;
	private String term;
	private boolean run;
	private LocalDateTime newdate;

}
