package com.library.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    private static final Logger logger = LogManager.getLogger(Hash.class);

    private static final String ALGORITHM = "MD5";

    public static String hash(String inputString) {
        String generatedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(inputString.getBytes());
            generatedPassword = convertBytesToHex(messageDigest.digest());;
        } catch (NoSuchAlgorithmException e) {
            logger.error(e);
        }
        return generatedPassword;
    }

    private static String convertBytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
