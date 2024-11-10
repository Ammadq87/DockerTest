package com.app.core.utils;

import com.app.core.exceptions.HashException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashingUtil {

    private HashingUtil() {
        // Empty constructor
    }

    public static String getHash(String s) throws HashException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(s.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new HashException(e.getMessage());
        }
    }
}

