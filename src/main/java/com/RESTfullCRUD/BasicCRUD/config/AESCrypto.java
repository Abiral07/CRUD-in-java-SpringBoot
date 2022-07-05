package com.RESTfullCRUD.BasicCRUD.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SerializationUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Configuration
public class AESCrypto implements AttributeConverter<Object, String> {
    @Value("${aes.encryption.key}")
    public String encryptionKey ; //16bte/ 8byte or 12byte or 16byte [1char = 2byte]
    public final String encryptionCipher = "AES";

    private static Key key;
    private static Cipher cipher;

    public Key getKey() {
        if (key == null) {
            key = new SecretKeySpec(encryptionKey.getBytes(), encryptionCipher);
        }

        return key;
    }

    //                                        GeneralSecurityException
    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (cipher == null) {
            cipher = Cipher.getInstance(encryptionCipher);
        }
        return cipher;
    }

    //to initialize cipher into memory
    public void initCipher(int cipherMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        getCipher().init(cipherMode, getKey());
    }

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Object attributes) {
        if (attributes == null)
            return null;
        initCipher(Cipher.ENCRYPT_MODE);
        byte[] bytes = SerializationUtils.serialize(attributes);
        assert bytes != null;
        return Base64.getEncoder().encodeToString(getCipher().doFinal(bytes));
    }

    @SneakyThrows
    @Override
    public Object convertToEntityAttribute(String s) {
        if (s == null)
            return null;
        initCipher(Cipher.DECRYPT_MODE);
        byte[] bytes = getCipher().doFinal(Base64.getDecoder().decode(s));
        return SerializationUtils.deserialize(bytes);
    }

}
