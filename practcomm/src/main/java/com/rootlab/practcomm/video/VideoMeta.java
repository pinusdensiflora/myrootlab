package com.rootlab.practcomm.video;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class VideoMeta {
	private int id;
	private String keyword;//?
	
	private String author;
	private LocalDateTime datetime;
	private int play_time;
	private String thumbnail;
	private String title;
	private String url;
}

/*
 * 
 * 
 * "author": "벤자민테스트",
    "datetime": "2024-11-26T12:57:21.000+09:00",
    "play_time": 5,
    "thumbnail": "https://search4.kakaocdn.net/argon/138x78_80_pr/DqVgdS1nrED",
    "title": "업로드테스트",
    "url": "https://tv.kakao.com/v/cvtbkzsykys6vz7oj77god90o"
 */
