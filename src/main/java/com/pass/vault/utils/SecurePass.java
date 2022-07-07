package com.pass.vault.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class used for encrypt and decrypt passwords
 */
public class SecurePass {

    private SecurePass() {
        throw new UnsupportedOperationException("El constructor vacio no está permitido");
    }

    // Algorithm to be used in decryption an encryption
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String SALT = "K6qjJl+sgJxTS6CtBrcBKw6QKgJklhpgaZO0ImSAUB0=";
    private static final char[] CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$¡?)&%$"
            .toCharArray();

    /**
     * Method to generatte the secret key to use in decryption and encryption
     * using the keyGeneral and salt
     * 
     * @param keyPass [String] key general
     * 
     * @return Secret Key
     */
    private static SecretKey getKeyFromPassword(String keyPass)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(keyPass.toCharArray(), SALT.getBytes(), 5000, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    /**
     * Method to generate the Initialization Vector
     */
    private static IvParameterSpec generateIV() {
        byte[] iv = new byte[16];
        return new IvParameterSpec(iv);
    }

    /**
     * Method to encrypt the given passwords
     * 
     * @param password [String] password to be encrypted
     * @param keyPass  [String] key for generate the secretkey
     * 
     * @return password encrypted
     */
    public static String encrypt(String password, String keyPass)
            throws NoSuchAlgorithmException, InvalidKeyException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            InvalidKeySpecException {

        SecretKey key = getKeyFromPassword(keyPass);
        IvParameterSpec iv = generateIV();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(password.getBytes());

        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * Method to decrypt the encrypted password
     * 
     * @param passwordEncrypt [String] password to be decrypted
     * @param keyPass         [String] key to generate the secretKey
     * 
     * @return password decrypted
     */
    public static String decrypt(String passwordEncrypt, String keyPass)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
            InvalidKeySpecException {

        SecretKey key = getKeyFromPassword(keyPass);
        IvParameterSpec iv = generateIV();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(passwordEncrypt));

        return new String(plainText);
    }

    public static String generateRandomPasword(int leng) {
        StringBuilder pasword = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < (leng - 4); i++) {
            pasword.append(CHARACTERS[random.nextInt(CHARACTERS.length)]);
        }

        pasword.insert(random.nextInt(pasword.length()), "Ñ");
        pasword.insert(random.nextInt(pasword.length()), CHARACTERS[random.nextInt(CHARACTERS.length)]);
        pasword.insert(random.nextInt(pasword.length()), CHARACTERS[random.nextInt(CHARACTERS.length)]);
        pasword.insert(random.nextInt(pasword.length()), "ñ");

        return pasword.toString();
    }
}
