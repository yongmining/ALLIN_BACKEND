package com.allin.filmface.jwt;

import java.security.SecureRandom;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        int keyLength = 512; // 비트 단위로 시크릿 키 길이 설정
        byte[] secretKeyBytes = generateSecretKey(keyLength);
        String secretKeyBase64 = encodeBase64(secretKeyBytes);
        System.out.println("Generated Secret Key: " + secretKeyBase64);
    }

    private static byte[] generateSecretKey(int keyLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretKey = new byte[keyLength / 8]; // 비트를 바이트로 변환
        secureRandom.nextBytes(secretKey);
        return secretKey;
    }

    private static String encodeBase64(byte[] data) {
        return java.util.Base64.getEncoder().encodeToString(data);
    }
}
