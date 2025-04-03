package com.example.jwt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class UIDGenerator {

    public static synchronized String generateUID() {
        StringBuilder hexString = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-","");
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");

            byte[] hash = sha.digest(uuid.getBytes());

            // 바이트 배열을 Hex 문자열로 변환
            for (int i = 0; i < 16; i++) {
                hexString.append(String.format("%02x", hash[i]));
            }

        } catch (NoSuchAlgorithmException nae) {
            throw new RuntimeException("SHA-256 not available");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hexString.toString();
    }
}
