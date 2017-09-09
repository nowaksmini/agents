package com.mini.smartroad.common;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoUtils {

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte aData : data) {
            int halfbyte = (aData >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = aData & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            byte[] sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String SHA256(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = messageDigest.digest(text.getBytes());
            return Base64.encodeBase64String(bytes);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String encodePassword(String password) {
        return CryptoUtils.SHA256(password);
    }

    public static String generateUserToken(String email) {
        return CryptoUtils.SHA1(email).substring(0, 20).replaceAll("\\+", "");
    }

    public static String generateStationToken(String name, double lon, double lat) {
        return CryptoUtils.SHA1(name + lon + lat).substring(0, 20).replaceAll("\\+", "");
    }

    public static String generateStationSecretCode(String userName, double lon, double lat) {
        return CryptoUtils.SHA1(userName + lon + lat).substring(0, 20).replaceAll("\\+", "");
    }
}
