package com.rootlab.practcomm.reservation;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WebDTO {
	private int id;
	private int reservation_id;
	private String contents;
	private LocalDateTime datetime;
	private String title;
	private String url;

}
