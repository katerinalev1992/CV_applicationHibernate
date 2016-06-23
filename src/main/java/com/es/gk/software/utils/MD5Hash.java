package com.es.gk.software.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by klevytska on 5/17/2016.
 */
public class MD5Hash {

    private static final String ALGORITHM = "md5";
    private static final String DIGEST_STRING = "HG58YZ3CR9";
    private static final String CHARSET_UTF_8 = "utf-8";
    private static final String SECRET_KEY_ALGORITHM = "DESede";
    private static final String TRANSFORMATION_PADDING = "DESede/CBC/PKCS5Padding";

    public static String md5Hash(String stringForHash){

        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try{
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(stringForHash.getBytes());
            digest = messageDigest.digest();

        }catch(NoSuchAlgorithmException e){
            //If algorithm doesn't exist
            e.printStackTrace();
        }

        BigInteger bigInteger = new BigInteger(1, digest);
        String md5Hex = bigInteger.toString(16);

        while(md5Hex.length() < 32){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

        /* Encryption Method */
        public static String encrypt(String message) throws Exception
        {
            final MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            final byte[] digestOfPassword = md.digest(DIGEST_STRING.getBytes(CHARSET_UTF_8));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
            }

            final SecretKey key = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher cipher = Cipher.getInstance(TRANSFORMATION_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            final byte[] plainTextBytes = message.getBytes(CHARSET_UTF_8);
            final byte[] cipherText = cipher.doFinal(plainTextBytes);

            return new String(cipherText);
        }

        /* Decryption Method */
        public static String decrypt(String message) throws Exception {
            final MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            final byte[] digestOfPassword = md.digest(DIGEST_STRING.getBytes(CHARSET_UTF_8));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
            }

            final SecretKey key = new SecretKeySpec(keyBytes, SECRET_KEY_ALGORITHM);
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher decipher = Cipher.getInstance(TRANSFORMATION_PADDING);
            decipher.init(Cipher.DECRYPT_MODE, key, iv);

            final byte[] plainText = decipher.doFinal(message.getBytes());

            return new String(plainText, CHARSET_UTF_8);
        }
}
