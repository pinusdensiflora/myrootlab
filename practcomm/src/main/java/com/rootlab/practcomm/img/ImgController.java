package com.rootlab.practcomm.img;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
import com.rootlab.practcomm.video.VideoMeta;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/img")
@RequiredArgsConstructor
public class ImgController {

	private final KakaoImg kakaoImg;
	Gson gson = new Gson(); 
	
	@GetMapping("")
	public String img() {
		return "img";
	}

	@GetMapping("/api")
	@ResponseBody
	public Map<String, Object> imgapi(@RequestParam(value = "keyword", defaultValue = "아이브") String keyword,
						 @RequestParam(value = "page", defaultValue = "1") int page,
						 @RequestParam(value = "sort", defaultValue = "accuracy") String sort,
						 @RequestParam(value = "size", defaultValue = "1") int size) {
		
		
		String reponse = kakaoImg.searchResult(keyword, page, sort, size);
		Map<String, Object> map = gson.fromJson(reponse, new TypeToken<Map<String, Object>>(){}.getType());
		
		//System.out.println(map.get("documents"));
		
		return map;

	}
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestBody List<ImgMeta> map){
		try {
	        System.out.println("받음: "+map);
	        return ResponseEntity.ok("성공");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("실패: " + e.getMessage());
	    }
	}
	
	
	
	
	
}
