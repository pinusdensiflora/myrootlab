package com.rootlab.practcomm.video;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



/* webClient (Spring WebFlux 의존성, 비동기에 유리, 라이징스타) 
 * OkHttp (모바일)
 * RestTemplate 
 * HttpClient
 * HttpURLConnection(표준, 경량)*/

//bean
@Service 
public class KakaoVideo {
	
	public String searchResult(String keyword, int page) {
		/*
query	String	검색을 원하는 질의어	O
sort	String	결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy	X
page	Integer	결과 페이지 번호, 1~15 사이의 값	X
size	Integer	한 페이지에 보여질 문서 수, 1~30 사이의 값, 기본 값 15	X
		 * */
		String sort = "recency";
		int size = 8;
		
		String baseUrl = "https://dapi.kakao.com/v2/search/vclip";
		String param = "?query=" + keyword
						+ "&sort=" + sort
						+ "&page=" + page
						+ "&size=" + size;
		
		baseUrl = baseUrl+param;
		
		WebClient webClient = WebClient.create();
		String response = webClient.get()
                			.uri(baseUrl)
                			.header("Authorization", "KakaoAK 6607476ec564e326ed2173713fb86ab1")
                			.retrieve()
                			.bodyToMono(String.class)
                			.block(); // 비동기 결과를 동기적으로 변환?
		System.out.println(response);
		
		return response;
		
	}
	
	public String videoForQuartz(String keyword, String sort) {

		int size = 3;
		
		String baseUrl = "https://dapi.kakao.com/v2/search/vclip";
		String param = "?query=" + keyword
						+ "&sort=" + sort
						+ "&size=" + size;
		
		baseUrl = baseUrl+param;
		
		WebClient webClient = WebClient.create();
		String response = webClient.get()
                			.uri(baseUrl)
                			.header("Authorization", "KakaoAK 6607476ec564e326ed2173713fb86ab1")
                			.retrieve()
                			.bodyToMono(String.class)
                			.block(); // 비동기 결과를 동기적으로 변환?

		System.out.println(response);
		
		return response;
		
	}
	

}
