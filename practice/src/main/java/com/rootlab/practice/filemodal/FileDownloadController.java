package com.rootlab.practice.filemodal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/filemodal")
@RequiredArgsConstructor
public class FileDownloadController {
	
	
	
	 private final FileMetaService fileMetaService;
	 
	@GetMapping("/download")
	public String download(Model model) {
		model.addAttribute("filemetas", fileMetaService.findAll());
		//System.out.println(fileMetaService.findAll());
		return "/filemodal/download";
	}
	
	@GetMapping("/download/Sample.pdf")
	public ResponseEntity<FileSystemResource> filedown() throws IOException {
		System.out.println("확인");
		
		String filePath = "src/main/resources/static/files/" + "Sample.pdf";
        File file = new File(filePath);
        
        
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        FileSystemResource resource = new FileSystemResource(file);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);		
		
	}
	
	@GetMapping("/download/{filename}")
	public ResponseEntity<FileSystemResource> filedown(@PathVariable("filename") String filename) throws IOException {
		
		
		FileMeta fm = fileMetaService.find(filename);

		String filePath = fm.getPath();
        File file = new File(filePath);
        
        
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        FileSystemResource resource = new FileSystemResource(file);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName()); //다운시에는 디코딩된 이름

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
		
	}
	
	
	
	


}
