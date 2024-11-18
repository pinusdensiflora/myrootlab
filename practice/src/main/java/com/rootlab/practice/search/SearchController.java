package com.rootlab.practice.search;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



@Controller
@RequestMapping("/search")
public class SearchController {

	
	KakaoSearch kakaoSearch = new KakaoSearch(); //객체를 여기서 생성?

	//pretty print 할 때 gson 또는 jackson을 사용함. 의존성 필요
	//https://mvnrepository.com/artifact/com.google.code.gson/gson/2.7
	Gson gson = new Gson(); // 기본 생성
	Gson prettyGson = new GsonBuilder().setPrettyPrinting().create(); // Pretty print 설정
	
	
	@GetMapping("")
	public String search() {
		return "/search/search";
	}
	
	@PostMapping("")
	@ResponseBody
	public String search(@RequestParam("keyword") String keyword) {
		
		System.out.println(keyword);
		String resultString = kakaoSearch.searchResult(keyword);
		String prettyJson = gson.toJson(gson.fromJson(resultString, Object.class));
		prettyJson = prettyJson.replaceAll("\\\\u003cb\\\\u003e", "");//<b>
		prettyJson = prettyJson.replaceAll("\\\\u003c/b\\\\u003e", "");//</b>
		//다른 것도 정리를 할 수 있는 ... 정규식을 마련해야겠음..
       System.out.println("최종 : "+prettyJson);
		return prettyJson;
		
	}
}
