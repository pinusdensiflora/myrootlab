package com.rootlab.practcomm.video;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMetaMapper {
	
	void save (VideoMeta videoMeta) ;
}
