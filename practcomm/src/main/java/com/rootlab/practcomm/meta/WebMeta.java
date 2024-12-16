package com.rootlab.practcomm.meta;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class WebMeta {
	private int id;
	private String contents;
	private LocalDateTime datetime;
	private String title;
	private String url;
	
}
