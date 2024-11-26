package com.rootlab.practice.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoSearch {
	
	
	@Value("${kakao.app.key}")
	private String kakao_app_key;
	
	//private String AK = "KakaoAK 6607476ec564e326ed2173713fb86ab1";

	public String searchResult(String keyword, Integer page) {
		String AK = "KakaoAK " + this.kakao_app_key;
		System.out.println(AK);
		String url = "https://dapi.kakao.com/v2/search/web" + "?query=" + keyword + "&page=" + page;
		
		

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", AK);

		
		HttpEntity<String> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//		System.out.println(response.getBody());
		System.out.println("완료");
		return response.getBody();
	}



}
