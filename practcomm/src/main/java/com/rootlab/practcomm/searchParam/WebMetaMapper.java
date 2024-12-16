package com.rootlab.practcomm.searchParam;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebMetaMapper {
	
	
	void save(WebMeta webMeta);
}
