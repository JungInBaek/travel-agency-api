package com.travel.agency.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class EncryptionUtils {

    private static final int SALT_SIZE = 16;

    public static String createSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] temp = new byte[SALT_SIZE];
        secureRandom.nextBytes(temp);
        return byteToString(temp);
    }

    public static String hashing(String password, String salt) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("잘못된 암호화 알고리즘입니다.", e);
        }
        return keyStretching(password.getBytes(), salt, md);
    }

    private static String keyStretching(byte[] password, String salt, MessageDigest md) {
        for (int i = 0; i < 10000; i++) {
            String temp = byteToString(password) + salt;
            md.update(temp.getBytes());
            password = md.digest();
        }
        return byteToString(password);
    }

    private static String byteToString(byte[] temp) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : temp) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

}