package com.rootlab.practcomm.reservation.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ImgDTO {
	private int id;
	private int reservation_id; //FK
	
	private String collection;
	private LocalDateTime datetime;
	private String display_sitename;
	private String doc_url;
	private int height;
	private String image_url;
	private String thumbnail_url;
	private int width;
	
	private LocalDateTime createtime;
	

}
