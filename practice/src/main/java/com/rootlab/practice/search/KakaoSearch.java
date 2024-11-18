package com.rootlab.practice.search;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class KakaoSearch {


	public String searchResult(String keyword) {

		String url = "https://dapi.kakao.com/v2/search/web" + "?query=" + keyword;
		
		

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK 6607476ec564e326ed2173713fb86ab1");

		
		HttpEntity<String> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		System.out.println(response.getBody());
		return response.getBody();
	}



}
