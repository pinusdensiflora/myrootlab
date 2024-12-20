package com.rootlab.practcomm.reservation.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rootlab.practcomm.reservation.dto.ImgDTO;

@Mapper
public interface ImgDTOMapper {
	void r_save(ImgDTO imgDTO);
}
