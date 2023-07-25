package com.travel.agency.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class EncryptionUtils {

    public static String encryptionSHA256(String s) {
        return encrypt(s, "SHA-256");
    }

    public static String encrypt(String s, String messageDigest) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(messageDigest + "는 없는 암호화 알고리즘입니다.", e);
        }
        md.reset();
        byte[] bytes = s.getBytes();
        StringBuilder sb = new StringBuilder();
        byte[] digested = md.digest(bytes);
        for (byte b : digested) {
            sb.append(Integer.toString((b&0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}