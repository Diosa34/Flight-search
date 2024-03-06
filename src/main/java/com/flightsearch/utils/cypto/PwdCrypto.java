package com.flightsearch.utils.cypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PwdCrypto {
    private static final String HASH_TEMPLATE = "%s:%s:%s";

    private static String encodeBytesToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String encodeHashToString(String algorithm, byte[] salt, byte[] hashedPassword) {
        String saltString = encodeBytesToString(salt);
        String hashedPasswordString = encodeBytesToString(hashedPassword);
        return String.format(
                HASH_TEMPLATE,
                algorithm,
                saltString,
                hashedPasswordString
        );
    }

    private static byte[] decodeStringToBytes(String string) {
        return Base64.getDecoder().decode(string);
    }

    private static byte[] createSalt(int size) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[size];
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] createPasswordHash(String pwd, String algorithm, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(salt);
            return md.digest(pwd.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ignore) {
            throw new PwdCryptoException(
                    "NoSuchAlgorithmException: Алгоритма шифрования \"" + algorithm + "\" не существует."
            );
        }
    }

    public static String getHash(String pwd) {
        return getHash(pwd, "SHA-512", 16);
    }

    public static String getHash(String pwd, String algorithm, int salt_length) {
        byte[] salt = createSalt(salt_length);
        byte[] passwordHash = createPasswordHash(pwd, algorithm, salt);
        return encodeHashToString(algorithm, salt, passwordHash);
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        String[] hashInfo = hashedPassword.split(":");
        if (hashInfo.length != 3) {
            throw new PwdCryptoException("Не верный формат хэша пароля.");
        }
        String algorithm = hashInfo[0];
        byte[] salt = decodeStringToBytes(hashInfo[1]);
        byte[] hashedPassword1 = decodeStringToBytes(hashInfo[2]);
        byte[] hashedPassword2 = createPasswordHash(password, algorithm, salt);
        return hashedPassword1 == hashedPassword2;
    }
}
