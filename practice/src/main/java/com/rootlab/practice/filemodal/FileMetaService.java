package com.rootlab.practice.filemodal;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileMetaService {
	private final FileMetaMapper fileMetaMapper;
	
	public void save(FileMeta filemeta) {
		fileMetaMapper.save(filemeta);
	}
	
	public List<FileMeta> find(String filename) {
		return fileMetaMapper.find(filename);				
	}
	
	public List<FileMeta> findAll() {
		return fileMetaMapper.findAll();				
	}

}
