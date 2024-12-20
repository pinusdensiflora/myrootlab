package com.rootlab.practcomm.reservation.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rootlab.practcomm.reservation.dto.VideoDTO;
import com.rootlab.practcomm.reservation.dto.WebDTO;
import com.rootlab.practcomm.reservation.mapper.VideoDTOMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoDTOService {

	private final VideoDTOMapper videoDTOMapper;
	
	public void r_save(VideoDTO videoDTO) {
		videoDTOMapper.r_save(videoDTO);
	}
	
	public void r_saveList(Object objectData, int rid) {
		System.out.println("서비스 진입 성공" + objectData.toString());
		
		if (objectData instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) objectData;

            // for문 사용
            for (Map<String, Object> map : list) {
            	VideoDTO videoDTO = new VideoDTO();

            	videoDTO.setAuthor(map.get("author").toString());
            	videoDTO.setTitle(map.get("title").toString());
            	videoDTO.setPlay_time((int)map.get("play_time"));
            	videoDTO.setUrl(map.get("url").toString());
            	videoDTO.setThumbnail(map.get("thumbnail").toString());
            	
            	// Step 1: OffsetDateTime으로 파싱
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(map.get("datetime").toString());   
                // Step 2: LocalDateTime으로 변환 (시간대 정보 제거)
                LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();

                videoDTO.setDatetime(localDateTime);
                videoDTO.setCreatetime(LocalDateTime.now());
            	
                videoDTO.setReservation_id(rid);
            	//System.out.println("저장 webDTO:" + webDTO);
            	videoDTOMapper.r_save(videoDTO);
            }
        } else {
            System.out.println("Object는 List가 아닙니다.");
        }
		
	}
	
}
