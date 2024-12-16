package com.rootlab.practcomm.reservation;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class VideoDTO {
	private int id;
	private int reservation_id;
	
	private String author;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")	
	private LocalDateTime datetime;
	private int play_time;
	private String thumbnail;
	private String title;
	private String url;

}
