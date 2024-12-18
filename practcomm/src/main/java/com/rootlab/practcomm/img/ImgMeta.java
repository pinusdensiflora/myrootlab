package com.rootlab.practcomm.img;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ImgMeta {

	private int id;
	private String collection;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")	
	private LocalDateTime datetime;
	private String display_sitename;
	private String doc_url;
	private int height;
	private String image_url;
	private String thumbnail_url;
	private int width;
	private String memo;
}
