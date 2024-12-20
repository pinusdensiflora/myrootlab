package com.rootlab.practcomm.reservation.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rootlab.practcomm.reservation.dto.VideoDTO;

@Mapper
public interface VideoDTOMapper {
	void r_save(VideoDTO videoDTO);
}
