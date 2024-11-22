package com.rootlab.practice.filemodal;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMetaMapper {
	public void save(FileMeta fileMeta);
}
