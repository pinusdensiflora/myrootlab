package com.rootlab.practparking.key;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KeyController {
	
	@GetMapping("/public-key")
	@ResponseBody
    public String getPublicKey() {
        try {
            // 공개키 파일 읽기
            String publicKeyPEM = new String(Files.readAllBytes(Paths.get("public_key.pem")));
           // System.out.println("공개키: \n"+publicKeyPEM);
            return publicKeyPEM; // 클라이언트에게 공개키를 반환
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading public key.";
        }
    }

}
