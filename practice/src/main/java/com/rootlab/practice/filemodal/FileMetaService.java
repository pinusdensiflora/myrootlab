package com.rootlab.practice.filemodal;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileMetaService {
	private final FileMetaMapper fileMetaMapper;
	
	public void save(FileMeta filemeta) {
		fileMetaMapper.save(filemeta);
	}

}
