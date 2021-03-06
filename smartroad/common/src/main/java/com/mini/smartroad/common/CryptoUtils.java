package com.mini.smartroad.common;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class CryptoUtils {

    private static final String algorithm = "DESede";
    private static Key key = null;
    private static Cipher cipher = null;
    private static Random random = new Random();

    static {
        try {
            key = KeyGenerator.getInstance(algorithm).generateKey();
            cipher = Cipher.getInstance(algorithm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] encrypt(String input)
            throws InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] inputBytes = input.getBytes();
        return cipher.doFinal(inputBytes);
    }

    public static String decrypt(byte[] encryptionBytes)
            throws InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] recoveredBytes
                = cipher.doFinal(encryptionBytes);
        String recovered
                = new String(recoveredBytes);
        return recovered;
    }

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
        return CryptoUtils.SHA1(new Date() + email).substring(0, 20).replaceAll("\\+", "");
    }

    public static String generateStationDetailsToken(String name, double longitude, double latitude) {
        return CryptoUtils.SHA1(new Date() + name + latitude + longitude).substring(0, 20).replaceAll("\\+", "");
    }

    public static String generateStationToken(String name) {
        return CryptoUtils.SHA1(new Date() + name).substring(0, 20).replaceAll("\\+", "");
    }

    public static String generateActionToken(ActionType actionType, String userToken, String stationToken) {
        return CryptoUtils.SHA1(new Date() + actionType.name() + userToken + stationToken).substring(0, 20).replaceAll("\\+", "");
    }

    public static String generateCouponToken(String actionToken) {
        return CryptoUtils.SHA1(new Date() + actionToken).substring(0, 20).replaceAll("\\+", "");
    }

    public static String generateDiscountCode(String couponToken) {
        return CryptoUtils.SHA1(new Date() + couponToken).substring(0, 8).replaceAll("\\+", "");
    }
}
