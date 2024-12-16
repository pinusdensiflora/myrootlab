package com.rootlab.practcomm.meta;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebMetaMapper {
	
	
	void save(WebMeta webMeta);
}
