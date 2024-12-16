package com.rootlab.practcomm.video;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoMetaService {

	private final VideoMetaMapper videoMetaMapper;
	
	public void save(VideoMeta videoMeta) {
		videoMetaMapper.save(videoMeta);
	}
	public void saveList(List<VideoMeta> list) {
		for(VideoMeta videometa : list) {
			save(videometa);
		}
	}
	
	
}
