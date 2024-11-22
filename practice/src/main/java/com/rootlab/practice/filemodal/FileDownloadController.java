package com.rootlab.practice.filemodal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filemodal")
public class FileDownloadController {
	
	
	 private final Path downloadDir = Paths.get("").toAbsolutePath();
	 
	 
	@GetMapping("/download")
	public String download() {
		return "/filemodal/download";
	}
	
	@GetMapping("/download/sample")
	public Resource filedown() throws IOException {
		
		System.out.println(downloadDir);
		
		Path file = downloadDir.resolve("Sample.pdf").normalize();
		return new UrlResource(file.toUri());
	}

}
