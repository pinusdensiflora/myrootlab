package com.rootlab.practcomm.meta;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebMetaService {
	
	private final WebMetaMapper webMetaMapper;
	
	//리스트를 저장
	public void save(Object data){
		
		System.out.println("map : "+data);
		
		
        if (data instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) data;

            // for문 사용
            for (Map<String, Object> map : list) {
            	WebMeta webMeta = new WebMeta();
//            	for(String key : map.keySet()) {
//            		System.out.println(map.get(key));
//            		
//            	}
            
            	webMeta.setContents(map.get("contents").toString());
            	webMeta.setTitle(map.get("title").toString());
            	webMeta.setUrl(map.get("url").toString());
            	
            	// Step 1: OffsetDateTime으로 파싱
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(map.get("datetime").toString());   
                // Step 2: LocalDateTime으로 변환 (시간대 정보 제거)
                LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();

                webMeta.setDatetime(localDateTime);
            	
            	//System.out.println("저장 webMeta:" + webMeta);
            	webMetaMapper.save(webMeta);
            }
        } else {
            System.out.println("Object는 List가 아닙니다.");
        }
		
	}
	

}
