package com.rootlab.practcomm.img;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rootlab.practcomm.video.VideoMeta;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImgMetaService {
	private final ImgMetaMapper imgMetaMapper;
	
	public void save(ImgMeta imgMeta) {
		imgMetaMapper.save(imgMeta);
	}
	public void saveList(List<ImgMeta> imgMetaList) {
		
		for(ImgMeta imgmeta : imgMetaList) {
			if(imgmeta != null) {
				imgMetaMapper.save(imgmeta);
			}
		}
	}
	
}
