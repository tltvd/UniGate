package com.example.unigate;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Security {
    private static final int SALT_LENGTH = 16;
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String ENCRYPTION_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final int KEY_SIZE = 128;

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        String saltedPassword = salt + password;
        MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] hashedPassword = messageDigest.digest(saltedPassword.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    public static String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String encryptPassword(String password, String key) throws Exception {
        Key secretKey = generateKey(key);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedPassword = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedPassword);
    }

    public static boolean validatePassword(String password, String salt, String hashedPassword, String key) throws Exception {
        String passwordToValidate = hashPassword(password, salt);
        String encryptedPasswordToValidate = encryptPassword(passwordToValidate, key);
        return Arrays.equals(Base64.getDecoder().decode(hashedPassword), Base64.getDecoder().decode(encryptedPasswordToValidate));
    }

    private static Key generateKey(String key) throws Exception {
        byte[] keyBytes = Arrays.copyOf(key.getBytes(), KEY_SIZE / 8);
        return new SecretKeySpec(keyBytes, "AES");
    }
}