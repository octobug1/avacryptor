package avacryptor.services;

import avacryptor.crypto.AESUtil;
import avacryptor.crypto.KeyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

public class FileEncryptionService {


    public void encryptFile(
            File inputFile,
            File outputFile,
            String password
    ) throws Exception {


        // Read original file
        byte[] fileBytes =
                Files.readAllBytes(
                        inputFile.toPath()
                );


        // Generate salt
        byte[] salt =
                KeyGenerator.generateSalt();


        // Generate encryption key
        byte[] key =
                KeyGenerator.generateKey(
                        password,
                        salt
                );


        // Encrypt file bytes
        byte[] encrypted =
                AESUtil.encryptBytes(
                        fileBytes,
                        key
                );


        // Save salt + encrypted data
        FileOutputStream fos =
                new FileOutputStream(outputFile);


        fos.write(salt);
        fos.write(encrypted);


        fos.close();
    }



    public void decryptFile(
            File encryptedFile,
            File outputFile,
            String password
    ) throws Exception {


        // Read encrypted file
        byte[] fileData =
                Files.readAllBytes(
                        encryptedFile.toPath()
                );


        // Extract salt
        byte[] salt =
                new byte[16];


        System.arraycopy(
                fileData,
                0,
                salt,
                0,
                16
        );


        // Extract encrypted content
        byte[] encryptedBytes =
                new byte[fileData.length - 16];


        System.arraycopy(
                fileData,
                16,
                encryptedBytes,
                0,
                encryptedBytes.length
        );


        // Generate the same key again
        byte[] key =
                KeyGenerator.generateKey(
                        password,
                        salt
                );


        // Decrypt file bytes
        byte[] fileBytes =
                AESUtil.decryptBytes(
                        encryptedBytes,
                        key
                );


        // Restore original file
        Files.write(
                outputFile.toPath(),
                fileBytes
        );
    }
}