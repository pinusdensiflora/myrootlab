package com.rootlab.practcomm.video;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

	private final KakaoVideo kakaoVideo;
	
	
	//json 형식의 String을 Map 으로 변환 (jackson 도 있는데 난 gson 씀)
	//webClient의 응답-> String
	Gson gson = new Gson(); 
	
	@GetMapping("")
	public String video() {
		return "video";
	}
	
	
	@GetMapping("/api")
	@ResponseBody
	public Map<String, Object> videoapi() {
		
		String response = kakaoVideo.searchResult("에스파", 1);
		
		// JSON 문자열을 Map<String, Object>로 변환
		Map<String, Object> map = gson.fromJson(response, new TypeToken<Map<String, Object>>(){}.getType());
				
		return map;
	}
	
}
