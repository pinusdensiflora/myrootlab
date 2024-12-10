package com.rootlab.practcomm.video;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	//요청 url 예시
	//http://localhost:8080/community/video/api?keyword=안녕하세요&page=1
	@GetMapping("/api")
	@ResponseBody
	public Map<String, Object> videoapi(
							@RequestParam(value = "keyword", defaultValue = "defaultKey") String keyword,
							@RequestParam(value = "page", defaultValue = "1") int page) {
		
		
		String response = kakaoVideo.searchResult(keyword, page);
		
		// JSON 문자열을 Map<String, Object>로 변환
		Map<String, Object> map = gson.fromJson(response, new TypeToken<Map<String, Object>>(){}.getType());
				
		return map;
	}
	
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestBody List<VideoMeta> map){
		System.out.println("저장할 VideoMeta 데이터 리스트 : " + map.toString());
		//return ResponseEntity.ok("ok");
		return ResponseEntity.badRequest().body("error 발생");
	}
	
	
}
