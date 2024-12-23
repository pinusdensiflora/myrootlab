package com.rootlab.practparking.user;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	
	public void save(User u) throws Exception {
		
		String encryptPassword = u.getPassword();
		String privateKeyPEM = null;
		String decryptPassword = decrypt(encryptPassword);//복호화

		// SHA-256 해시 생성
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(decryptPassword.getBytes());
        
        // 해시를 16진수 문자열로 변환
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        
        System.out.println("SHA-256 hashed password: " + hexString.toString());
		
		u.setEnabled(true);
		u.setRole_code(100);
		u.setCreatetime(LocalDateTime.now());
		u.setPassword(hexString.toString());
		userMapper.save(u);
	}
	
	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}
	
	
	
	
	private String decrypt(String encryptedData) throws Exception {
		try {
	
			// 개인키 파일을 읽어서 PrivateKey 객체 생성
	        String privateKeyPEM = new String(Files.readAllBytes(Paths.get("private_key.pem")));
//	        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "")
//	                .replace("\n-----END PRIVATE KEY-----", "");
	        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPEM);
	        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

	        // 암호화된 데이터 디코딩
	        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);

	        // RSA 복호화
	        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        byte[] decryptedBytes = cipher.doFinal(encryptedBytes); // 복호화된 데이터

	        // 복호화된 데이터 출력
	        String decryptedData = new String(decryptedBytes, "UTF-8");
	        System.out.println("복호화된 데이터: " + decryptedData);
	        return decryptedData;
	        

        } catch (NoSuchAlgorithmException e) {
        	
            System.err.println("지원되지 않는 알고리즘입니다: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            System.err.println("잘못된 키 형식입니다: " + e.getMessage());
        } catch (InvalidKeyException e) {
            System.err.println("유효하지 않은 키입니다: " + e.getMessage());
        } catch (BadPaddingException e) {
        	//e.printStackTrace();
            System.err.println("패딩 오류: 암호화 데이터가 손상되었거나 패딩이 맞지 않습니다: " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            System.err.println("잘못된 블록 크기입니다: 암호화된 데이터의 크기가 올바르지 않습니다: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("알 수 없는 오류가 발생했습니다: " + e.getMessage());
        }

        return "";
        
	}
	
	private String hash() {
		return "";
	}
	
	
}
