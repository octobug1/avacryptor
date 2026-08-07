package avacryptor.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtil {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;


    // =========================
    // TEXT ENCRYPTION
    // =========================

    public static String encrypt(String message, byte[] key) throws Exception {

        return Base64.getEncoder()
                .encodeToString(
                        encryptBytes(
                                message.getBytes(StandardCharsets.UTF_8),
                                key
                        )
                );
    }



    // =========================
    // TEXT DECRYPTION
    // =========================

    public static String decrypt(String encryptedText, byte[] key)
            throws Exception {

        byte[] decrypted =
                decryptBytes(
                        Base64.getDecoder()
                                .decode(encryptedText),
                        key
                );


        return new String(
                decrypted,
                StandardCharsets.UTF_8
        );
    }



    // =========================
    // FILE ENCRYPTION
    // =========================

    public static byte[] encryptBytes(
            byte[] data,
            byte[] key
    ) throws Exception {


        byte[] iv =
                new byte[IV_LENGTH];

        new SecureRandom()
                .nextBytes(iv);


        Cipher cipher =
                Cipher.getInstance(ALGORITHM);


        SecretKeySpec secretKey =
                new SecretKeySpec(
                        key,
                        "AES"
                );


        cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey,
                new GCMParameterSpec(
                        TAG_LENGTH,
                        iv
                )
        );


        byte[] encrypted =
                cipher.doFinal(data);


        byte[] output =
                new byte[iv.length + encrypted.length];


        System.arraycopy(
                iv,
                0,
                output,
                0,
                iv.length
        );


        System.arraycopy(
                encrypted,
                0,
                output,
                iv.length,
                encrypted.length
        );


        return output;
    }



    // =========================
    // FILE DECRYPTION
    // =========================

    public static byte[] decryptBytes(
            byte[] encryptedData,
            byte[] key
    ) throws Exception {


        byte[] iv =
                new byte[IV_LENGTH];


        System.arraycopy(
                encryptedData,
                0,
                iv,
                0,
                IV_LENGTH
        );


        byte[] encrypted =
                new byte[encryptedData.length - IV_LENGTH];


        System.arraycopy(
                encryptedData,
                IV_LENGTH,
                encrypted,
                0,
                encrypted.length
        );


        Cipher cipher =
                Cipher.getInstance(ALGORITHM);


        SecretKeySpec secretKey =
                new SecretKeySpec(
                        key,
                        "AES"
                );


        cipher.init(
                Cipher.DECRYPT_MODE,
                secretKey,
                new GCMParameterSpec(
                        TAG_LENGTH,
                        iv
                )
        );


        return cipher.doFinal(encrypted);
    }
}