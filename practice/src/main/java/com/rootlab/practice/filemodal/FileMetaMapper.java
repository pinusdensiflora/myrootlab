package com.rootlab.practice.filemodal;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMetaMapper {
	void save(FileMeta fileMeta);
	List<FileMeta> find(String filename);
}
