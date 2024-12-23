package com.rootlab.practparking;

import java.io.FileWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PractparkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PractparkingApplication.class, args);
		
		
		try {
            // RSA 키 쌍 생성
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048); // 2048비트 키 길이
            KeyPair keyPair = keyGen.generateKeyPair();

            // 개인키와 공개키를 PEM 형식으로 변환
            String privateKeyPEM = "-----BEGIN PRIVATE KEY-----\n" +
                    Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()) +
                    "\n-----END PRIVATE KEY-----";
            String publicKeyPEM = "-----BEGIN PUBLIC KEY-----\n" +
                    Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()) +
                    "\n-----END PUBLIC KEY-----";

            // 개인키와 공개키를 파일로 저장
            try (FileWriter privateKeyWriter = new FileWriter("private_key.pem"); //프로젝트 위치
                 FileWriter publicKeyWriter = new FileWriter("public_key.pem")) {
                privateKeyWriter.write(privateKeyPEM);
                publicKeyWriter.write(publicKeyPEM);
            }

            System.out.println("키 쌍이 생성되고 파일에 저장되었습니다.");

        } catch (Exception e) {
            //e.printStackTrace();
        }
	}
	
	private void createKey() {
		
		
	}


}
