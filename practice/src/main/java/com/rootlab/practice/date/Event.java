package com.rootlab.practice.date;

import java.security.KeyStore.PrivateKeyEntry;
import java.time.LocalDate;
//import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.Data;

@Data
public class Event {

	private int id;
	private String title;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate start;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate end;
	
}
