package avacryptor.services;

import avacryptor.crypto.AESUtil;
import avacryptor.crypto.KeyGenerator;

import java.util.Base64;

public class EncryptionService {


    public String encrypt(
            String message,
            String password
    ) throws Exception {


        byte[] salt =
                KeyGenerator.generateSalt();


        byte[] key =
                KeyGenerator.generateKey(
                        password,
                        salt
                );


        String encrypted =
                AESUtil.encrypt(
                        message,
                        key
                );


        return Base64.getEncoder()
                .encodeToString(salt)
                + ":"
                + encrypted;
    }




    public String decrypt(
            String encryptedData,
            String password
    ) throws Exception {


        String[] parts =
                encryptedData.split(":");


        if(parts.length != 2) {
            throw new Exception(
                    "Invalid encrypted format"
            );
        }


        byte[] salt =
                Base64.getDecoder()
                .decode(parts[0]);


        byte[] key =
                KeyGenerator.generateKey(
                        password,
                        salt
                );


        return AESUtil.decrypt(
                parts[1],
                key
        );
    }
}