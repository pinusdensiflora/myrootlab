package com.rootlab.practcomm.reservation.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.rootlab.practcomm.reservation.dto.ImgDTO;
import com.rootlab.practcomm.reservation.dto.VideoDTO;
import com.rootlab.practcomm.reservation.mapper.ImgDTOMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImgDTOService {

	private final ImgDTOMapper imgDTOMapper;

	public void r_save(ImgDTO imgDTO) {
		imgDTOMapper.r_save(imgDTO);
	}

	public void r_saveList(Object objectData, int rid) {

		if (objectData instanceof List) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) objectData;

			// for문 사용
			for (Map<String, Object> map : list) {
				ImgDTO i = new ImgDTO();

				i.setCollection(map.get("collection").toString());
				i.setDisplay_sitename(map.get("display_sitename").toString());
				i.setDoc_url(map.get("doc_url").toString());
				i.setImage_url(map.get("image_url").toString());
				i.setThumbnail_url(map.get("thumbnail_url").toString());

				
				Double d = (Double) map.get("height"); //Object -> Double 
            	//System.out.println(d + " : "+ map.get("play_time").getClass());//Double 임	
				i.setHeight(d.intValue());
				d = (Double) map.get("width");
				i.setWidth(d.intValue());

				// Step 1: OffsetDateTime으로 파싱
				OffsetDateTime offsetDateTime = OffsetDateTime.parse(map.get("datetime").toString());
				// Step 2: LocalDateTime으로 변환 (시간대 정보 제거)
				LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();

				i.setDatetime(localDateTime);
				i.setCreatetime(LocalDateTime.now());

				i.setReservation_id(rid);
				
				imgDTOMapper.r_save(i);
				
			}
		} else {
			System.out.println("Object는 List가 아닙니다.");
		}

	}
}
