package com.rootlab.practcomm.reservation.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class VideoDTO {
	private int id;
	private int reservation_id;//FK
	
	private String author;
	private LocalDateTime datetime;
	private int play_time;
	private String thumbnail;
	private String title;
	private String url;
	private LocalDateTime createtime;

}
