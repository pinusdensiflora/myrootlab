package com.rootlab.practice.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

	@GetMapping("")
	public String file() {
		return "/file/file";
	}
	/*
	@PostMapping("/upload")
	public String file(@RequestParam("files1[]") MultipartFile[] files1,
			@RequestParam("files2[]") MultipartFile[] files2) {

		// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html
		for (MultipartFile file : files1) {
			if (!file.isEmpty()) {
				try {

					// 파일 이름 가져오기
					String fileName = file.getOriginalFilename();
					// 파일 크기 가져오기
					long fileSize = file.getSize();

					// 파일 내용을 바이트 배열로 읽기
					byte[] bytes = file.getBytes();
					System.out.println(fileName + " " + fileSize + " " + bytes);
					// 파일을 특정 경로에 저장하기
					Path path = Paths.get("/home/dawon/_dev/download/" + fileName);// 여기에 저장 // ㄱ 같은 이름이 있다면?
					Files.write(path, bytes);

					System.out.println("Uploaded file: " + fileName + " (" + fileSize + " bytes)");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Empty file: " + file.getOriginalFilename());
			}
		}

		return "redirect:/file";
	}*/

}
