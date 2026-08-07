package avacryptor.crypto;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import java.security.SecureRandom;

public class KeyGenerator {


    public static byte[] generateKey(
            String password,
            byte[] salt
    ) throws Exception {


        PBEKeySpec spec =
                new PBEKeySpec(
                        password.toCharArray(),
                        salt,
                        65536,
                        256
                );


        SecretKeyFactory factory =
                SecretKeyFactory.getInstance(
                        "PBKDF2WithHmacSHA256"
                );


        SecretKey key =
                factory.generateSecret(spec);


        return key.getEncoded();
    }

    public static String generatePassword(){

    String characters =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789" +
            "!@#$%^&*()-_=+";

    SecureRandom random =
            new SecureRandom();

    StringBuilder password =
            new StringBuilder();


    for(int i = 0; i < 16; i++){

        int index =
                random.nextInt(
                        characters.length()
                );

        password.append(
                characters.charAt(index)
        );
    }


    return password.toString();
}



    public static byte[] generateSalt(){

        byte[] salt =
                new byte[16];

        new SecureRandom()
                .nextBytes(salt);

        return salt;
    }
}