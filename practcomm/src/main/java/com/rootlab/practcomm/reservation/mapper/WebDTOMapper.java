package com.rootlab.practcomm.reservation.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.rootlab.practcomm.reservation.dto.WebDTO;

@Mapper
public interface WebDTOMapper {
	void r_save(WebDTO webDTO);

}
