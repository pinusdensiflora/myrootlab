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
		/*for (MultipartFile file : files1) {
			if (!file.isEmpty()) {
				try {

					// 파일 이름(확장자 포함) 가져오기
					String fileName = file.getOriginalFilename();
				
					boolean duplication = false;
					if(!fileMetaService.find(fileName).isEmpty()) {
						duplication = true; //0이 아님
					}
					
					// 파일 크기 가져오기
					long fileSize = file.getSize();
					
					
					Pattern typeReg = Pattern.compile("\\.([a-z]+)$");
					Matcher matcher = typeReg.matcher(fileName);
					String fileType = "";
					if (matcher.find()) {
						fileType = matcher.group(1);	
						System.out.println(fileType);
			        }

					//순수 파일 명
					fileName = fileName.substring(0,fileName.length()-fileType.length()-1);

					if(duplication) {//중복이있는 경우
						fileName = indexName(fileName, fileType);
					}
					else {
						fileName = file.getOriginalFilename();//원복
					}
					
					// 파일 내용을 바이트 배열로 읽기
					byte[] bytes = file.getBytes();
					
					// 파일을 특정 경로에 저장하기
					Path path = Paths.get("C:\\_dev\\download\\" + fileName);// 여기에 저장 // ㄱ 같은 이름이 있다면? > uuid또는 인덱스 ㅂ튀이기
					Files.write(path, bytes);
				
					FileMeta filemeta = FileMeta.builder()
												.name(fileName)
												.size(fileSize)
												.type(fileType)
												.createDateTime(LocalDateTime.now())
												.path(path.toString())
												.build();
				
					fileMetaService.save(filemeta);
					//System.out.println(filemeta.toString());
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Empty file: " + file.getOriginalFilename());
			}
		}*/
		saveFile(files1);
		saveFile(files2);
		return "redirect:/";
	}

	
	private String indexName(String filename, String fileType) {
		int index = 1;
		String newFilename = filename+"."+fileType;
		
		while(!fileMetaService.find(newFilename).isEmpty()) {
			//비지 않았을때 index 갱신
			System.out.print("loop ");
			newFilename = filename + "(" + index + ")" + "." + fileType;
			index ++;
		}

		return newFilename;
	}
	
	private void saveFile(MultipartFile[] files) {
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				try {

					// 파일 이름(확장자 포함) 가져오기
					String fileName = file.getOriginalFilename();
				
					boolean duplication = false;
					if(!fileMetaService.find(fileName).isEmpty()) {
						duplication = true; //0이 아님
					}
					
					// 파일 크기 가져오기
					long fileSize = file.getSize();
					
					
					Pattern typeReg = Pattern.compile("\\.([a-z]+)$");
					Matcher matcher = typeReg.matcher(fileName);
					String fileType = "";
					if (matcher.find()) {
						fileType = matcher.group(1);	
						System.out.println(fileType);
			        }

					//순수 파일 명
					fileName = fileName.substring(0,fileName.length()-fileType.length()-1);

					if(duplication) {//중복이있는 경우
						fileName = indexName(fileName, fileType);
					}
					else {
						fileName = file.getOriginalFilename();//원복
					}
					
					// 파일 내용을 바이트 배열로 읽기
					byte[] bytes = file.getBytes();
					
					// 파일을 특정 경로에 저장하기
					Path path = Paths.get("C:\\_dev\\download\\" + fileName);// 여기에 저장 // ㄱ 같은 이름이 있다면? > uuid또는 인덱스 ㅂ튀이기
					Files.write(path, bytes);
				
					FileMeta filemeta = FileMeta.builder()
												.name(fileName)
												.size(fileSize)
												.type(fileType)
												.createDateTime(LocalDateTime.now())
												.path(path.toString())
												.build();
				
					fileMetaService.save(filemeta);
					//System.out.println(filemeta.toString());
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Empty file: " + file.getOriginalFilename());
			}
		}
	}
	
}
