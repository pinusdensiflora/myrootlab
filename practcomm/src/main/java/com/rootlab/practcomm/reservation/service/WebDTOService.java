package com.rootlab.practcomm.reservation.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rootlab.practcomm.reservation.dto.WebDTO;
import com.rootlab.practcomm.reservation.mapper.WebDTOMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebDTOService {

	private final WebDTOMapper webDTOMapper;
	
	public void r_save(WebDTO webDTO) {
	
		webDTOMapper.r_save(webDTO);
	}
	
	
	public void r_saveList(Object objectData, int rid) {
		
		if (objectData instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) objectData;

            // for문 사용
            for (Map<String, Object> map : list) {
            	WebDTO webDTO = new WebDTO();

            	webDTO.setContents(map.get("contents").toString());
            	webDTO.setTitle(map.get("title").toString());
            	webDTO.setUrl(map.get("url").toString());
            	
            	// Step 1: OffsetDateTime으로 파싱
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(map.get("datetime").toString());   
                // Step 2: LocalDateTime으로 변환 (시간대 정보 제거)
                LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();

                webDTO.setDatetime(localDateTime);
            	webDTO.setCreatetime(LocalDateTime.now());
            	
            	webDTO.setReservation_id(rid);
            	//System.out.println("저장 webDTO:" + webDTO);
            	webDTOMapper.r_save(webDTO);
            }
        } else {
            System.out.println("Object는 List가 아닙니다.");
        }
	}

}
