package com.rootlab.practcomm.img;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoImg {

	public String searchResult(String keyword, int page, String sort, int size) {
		//String sort = "accuracy";
		//int size = 8;
		
		String baseUrl = "https://dapi.kakao.com/v2/search/image";
		String param = "?query=" + keyword
						+ "&sort=" + sort
						+ "&page=" + page
						+ "&size=" + size;
		
		baseUrl = baseUrl+param;
		//System.out.println(baseUrl);
		
		WebClient webClient = WebClient.create();
		
		String response = webClient.get()
                			.uri(baseUrl)
                			.header("Authorization", "KakaoAK 6607476ec564e326ed2173713fb86ab1")
                			.retrieve()
                			.bodyToMono(String.class)
                			.block();

		//System.out.println(response);
		
		
		return response;
		
	}



}
