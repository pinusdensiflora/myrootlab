package com.rootlab.practice.filemodal;


import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileMeta {

	private int id;
	private String name;
	private long size; //q바이트 단위로 저장예정
	private String type;
	private LocalDateTime createDateTime;
	private String path; //저장 위치
	private String link;

	
}

/*
 * 테이블 생성 쿼리
CREATE TABLE filemeta(
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	size LONG,
	type VARCHAR(255),
	createtime DATETIME,
	path VARCHAR(255),
	link VARCHAR(255)
);
 * */


/*
 * 1. 기본 메타정보
파일 이름: 사용자가 업로드한 원본 파일의 이름.
파일 크기: 파일의 크기 (바이트 단위).
파일 형식: MIME 타입 또는 파일 확장자 (예: .jpg, .pdf 등).
업로드 날짜 및 시간: 파일이 서버에 업로드된 날짜와 시간.
2. 사용자 관련 정보
업로드한 사용자 ID: 파일을 업로드한 사용자의 고유 식별자.
사용자 이름: 파일을 업로드한 사용자의 이름 (선택적).
3. 파일 경로 및 위치
저장 경로: 서버에서 파일이 저장된 경로.
URL: 파일에 접근할 수 있는 URL (웹 애플리케이션에서 파일을 제공할 경우).
4. 상태 및 관리 정보
파일 상태: 파일의 현재 상태 (예: 활성, 비활성, 삭제됨 등).
버전 정보: 파일의 버전 관리 정보 (필요한 경우).
태그 또는 카테고리: 파일을 분류하기 위한 태그나 카테고리 정보.
5. 보안 및 접근 제어
암호화 여부: 파일이 암호화되었는지 여부.
접근 권한: 파일에 대한 접근 권한 정보 (예: 공개, 비공개, 특정 사용자만 접근 가능 등).
6. 추가 메타정보
파일 설명: 파일에 대한 간단한 설명이나 주석.
업로드 IP 주소: 파일을 업로드한 사용자의 IP 주소 (보안 감사용).
파일 해시값: 파일의 무결성을 확인하기 위한 해시값 (예: MD5, SHA-256 등).
 * */


