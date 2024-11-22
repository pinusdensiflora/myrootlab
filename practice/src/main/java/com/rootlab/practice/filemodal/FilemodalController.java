package com.rootlab.practice.filemodal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/filemodal")
@RequiredArgsConstructor
public class FilemodalController {

	private final FileMetaService fileMetaService;
	
	@GetMapping("")
	public String file() {
		return "/filemodal/filemodal";
	}

	@PostMapping("/upload")
	public String file(@RequestParam("files1[]") MultipartFile[] files1,
			@RequestParam("files2[]") MultipartFile[] files2) {

		// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html
		//for(MultipartFile[] files : [files1, files2] 배열 생성해서..?)
		for (MultipartFile file : files1) {
			if (!file.isEmpty()) {
				try {
					
					
					
			
					// 파일 이름 가져오기
					String fileName = file.getOriginalFilename();
					// 파일 크기 가져오기
					long fileSize = file.getSize();
					
					
					Pattern typeReg = Pattern.compile("\\.([a-z]+)$");
					Matcher matcher = typeReg.matcher(fileName);
					String fileType = "";
					if (matcher.find()) {
						fileType = matcher.group(1);	
						System.out.println(fileType);
			        }
					
					
					// 파일 내용을 바이트 배열로 읽기
					byte[] bytes = file.getBytes();
					System.out.println(fileName + " " + fileSize + " " + bytes);
					// 파일을 특정 경로에 저장하기
					Path path = Paths.get("C:\\_dev\\download\\" + fileName);// 여기에 저장 // ㄱ 같은 이름이 있다면? > uuid
					Files.write(path, bytes);

					System.out.println("Uploaded file: " + fileName + " (" + fileSize + " bytes)");
				
					FileMeta filemeta = FileMeta.builder()
												.name(fileName)
												.size(fileSize)
												.type(fileType)
												.createDateTime(LocalDateTime.now())
												.path(path.toString())
												
												.build();
				
					
					fileMetaService.save(filemeta);
					System.out.println(filemeta.toString());
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Empty file: " + file.getOriginalFilename());
			}
		}

		return "redirect:/";
	}

}
