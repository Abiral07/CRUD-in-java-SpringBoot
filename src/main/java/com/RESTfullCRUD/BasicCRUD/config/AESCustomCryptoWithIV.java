package com.RESTfullCRUD.BasicCRUD.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
@Configuration
public class AESCustomCryptoWithIV {
    private SecretKey key;

    public SecretKey getKey() {
        if (key == null) {
            //    @Value("${aes.encryption.key}")
            // n = 128 || 192 || 256
            String encryptionKey = "1234567890123456";
            key = new SecretKeySpec(encryptionKey.getBytes(), "AES");
        }

        return key;
    }

//    //----------------IV generation
//    public static IvParameterSpec generateIV(){
//        byte[] iv =new byte[16];        // iv is of 128bits[16bytes]
//        new SecureRandom().nextBytes(iv);   //initializing iv with 16bytes of secure random value
//        return new IvParameterSpec(iv);     //converting iv to IvParameterSpec
//    }

    //----------------Secret Key Generation----------------
// for key either use your own key or generate key using some function
//    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(n);
//        SecretKey key ;
//        return (key = keyGenerator.generateKey());
//    }
//    public static SecretKey getKeyFromPassword(String password, String salt)
//            throws NoSuchAlgorithmException, InvalidKeySpecException {
//
//        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);     //65,536 iterations and a key length of 256 bits:
//        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
//    }

    //------------------ENCRYPT--------------------
    public String encrypt(String algorithm, String input)
            throws NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, this.getKey(), new IvParameterSpec(new byte[16]));
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }
    //------------------DECRYPT-------------------
    public String decrypt(String algorithm, String cipherText)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, this.getKey(), new IvParameterSpec(new byte[16]));
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }
}
