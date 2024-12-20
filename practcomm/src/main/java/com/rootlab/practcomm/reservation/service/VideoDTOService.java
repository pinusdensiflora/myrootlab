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
		
		
		if (objectData instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) objectData;

            // for문 사용
            for (Map<String, Object> map : list) {
            	VideoDTO v = new VideoDTO();

            	v.setAuthor(map.get("author").toString());
            	v.setTitle(map.get("title").toString());
            	//v.setPlay_time((int)(map.get("play_time")));
            	Double d = (Double) map.get("play_time"); //Object -> Double 
            	//System.out.println(d + " : "+ map.get("play_time").getClass());//Double 임
            	v.setPlay_time(d.intValue());//Double -> int
            	v.setUrl(map.get("url").toString());
            	v.setThumbnail(map.get("thumbnail").toString());
            	
            	// Step 1: OffsetDateTime으로 파싱
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(map.get("datetime").toString());   
                // Step 2: LocalDateTime으로 변환 (시간대 정보 제거)
                LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();

                v.setDatetime(localDateTime);
                v.setCreatetime(LocalDateTime.now());
            	
                v.setReservation_id(rid);
                //System.out.println("비디오 확인" + v.toString());
            	videoDTOMapper.r_save(v);
            }
        } else {
            System.out.println("Object는 List가 아닙니다.");
        }
		
	}
	
}
