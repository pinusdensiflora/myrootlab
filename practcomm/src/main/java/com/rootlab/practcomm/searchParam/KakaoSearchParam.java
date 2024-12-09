package com.rootlab.practcomm.searchParam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


public class KakaoSearchParam {
	
	
	public String searchResult(String keyword, Integer page, Integer size) {

			//10개씩
		String url = "https://dapi.kakao.com/v2/search/web" + "?query=" + keyword + "&page=" + page + "&size="+size;
		
		

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
