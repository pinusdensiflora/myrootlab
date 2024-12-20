package com.rootlab.practcomm.reservation.service;

import org.springframework.stereotype.Service;

import com.rootlab.practcomm.reservation.dto.VideoDTO;
import com.rootlab.practcomm.reservation.mapper.VideoDTOMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoDTOService {

	private final VideoDTOMapper videoDTOMapper;
	
	public void r_save(VideoDTO videoDTO) {
		videoDTOMapper.r_save(videoDTO);
	}
	
	public void r_saveList(Object objectData) {
		
	}
	
}
