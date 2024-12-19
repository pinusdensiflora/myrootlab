package com.rootlab.practcomm.img;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImgMetaMapper {
	
	void save (ImgMeta imgMeta) ;

}
