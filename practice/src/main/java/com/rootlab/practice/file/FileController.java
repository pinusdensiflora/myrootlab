package com.rootlab.practice.file;

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
	@PostMapping("/upload")
	public String file(@RequestParam("files1[]") MultipartFile[] files1,
            			  @RequestParam("files2[]") MultipartFile[] files2) {
		
	
		return "/file/file" ;
	}

}
