package com.rootlab.practice.filemodal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMetaMapper {
	void save(FileMeta fileMeta);
	FileMeta find(String filename);
	List<FileMeta> findAll();
	
}
