package com.learnwy.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 25973 on 2017-05-17.
 */
public class RSAUtils {
    public static void main(String[] args) {

    }

    public static String[] getKeys() {
        String[] keys = new String[2];
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            keys[0] = keyPair.getPrivate().getFormat();
            keys[1] = keyPair.getPublic().getFormat();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return keys;
    }
}
