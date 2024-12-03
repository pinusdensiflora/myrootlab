package com.rootlab.practcomm.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


public class KakaoSearch {
	
	
	public String searchResult(String keyword, Integer page) {

		String url = "https://dapi.kakao.com/v2/search/web" + "?query=" + keyword + "&page=" + page + "&size=50";
		
		

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK 6607476ec564e326ed2173713fb86ab1");

		
		HttpEntity<String> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//		System.out.println(response.getBody());
		System.out.println("완료");
		return response.getBody();
		
	}

}
