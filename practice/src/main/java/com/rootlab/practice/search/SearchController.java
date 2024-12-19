package com.rootlab.practice.search;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ch.qos.logback.core.util.SystemInfo;
import lombok.RequiredArgsConstructor;



@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
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
	

	@GetMapping("/get")
	@ResponseBody
	public Map<String, Object> searchget(@RequestParam("keyword") String keyword,
										   @RequestParam("page") Integer page)  {
		
		
		System.out.println(keyword + " " + page);

		String prettyJson = kakaoSearch.searchResult(keyword, page);


		prettyJson = prettyJson.replaceAll("\\\\u003cb\\\\u003e", "");//<b>
		prettyJson = prettyJson.replaceAll("\\\\u003c/b\\\\u003e", "");//</b>

		
		Map<String, Object> mapData = gson.fromJson(prettyJson, new TypeToken<Map<String, Object>>(){}.getType());

		
		System.out.println(mapData);
		return mapData;
		
		
	}
	
	

	
	
/*	@PostMapping("")
	@ResponseBody
	public Map<String, Object> search(@RequestParam("keyword") String keyword,
										   @RequestParam("page") Integer page)  {
		
		System.out.println(keyword + " " + page);

				
				
		String resultString = kakaoSearch.searchResult(keyword, page);
		String prettyJson = gson.toJson(gson.fromJson(resultString, Object.class));
//		prettyJson = prettyJson.replaceAll("\\\\u003cb\\\\u003e", "");//<b>
//		prettyJson = prettyJson.replaceAll("\\\\u003c/b\\\\u003e", "");//</b>
//		prettyJson = prettyJson.replaceAll("\\\\u0026", "&");//
//		prettyJson = prettyJson.replaceAll("\\\\u003d", "=");//
		
		prettyJson = prettyJson.replaceAll("\\\\u003cb\\\\u003e", "");//<b>
		prettyJson = prettyJson.replaceAll("\\\\u003c/b\\\\u003e", "");//</b>
		
		Map<String, Object> mapData = gson.fromJson(prettyJson, new TypeToken<Map<String, Object>>(){}.getType());
       //System.out.println("map 형식 : " + mapData);
		//System.out.println("map에서 json다시 변환 : "+ gson.toJson(mapData));
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("documents", mapData.get("documents"));
		resultMap.put("meta", mapData.get("meta"));//최종페이지 값을 이렇게 넣기엔 좀...음...
		System.out.println("resultMap : " + resultMap);
		
		
		return resultMap;
		
	}*/
	
	

}
