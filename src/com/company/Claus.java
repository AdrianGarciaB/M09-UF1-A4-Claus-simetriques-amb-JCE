package com.company;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Claus {
    private static Claus claus;
    private SecretKey sKey;

    private SecretKey keygenKeyGeneration(int keySize) {
        sKey = null;
        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
            try {
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(keySize);
                sKey = kgen.generateKey();

            } catch (NoSuchAlgorithmException ex) {
                System.err.println("Generador no disponible.");
            }
        }
        return sKey;
    }

    public byte[] encryptData(byte[] data) {
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sKey);
            encryptedData =  cipher.doFinal(data);
        } catch (Exception  ex) {
            System.err.println("Error xifrant les dades: " + ex);
        }
        return encryptedData;
    }

    public byte[] decryptData(byte[] data) {
        byte[] decryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sKey);
            decryptedData =  cipher.doFinal(data);
        } catch (Exception  ex) {
            System.err.println("Error desxifrant les dades: " + ex);
        }

        return decryptedData;
    }

    private SecretKey passwordKeyGeneration(String passphrase, int keySize) {
        sKey = null;
        if ((keySize == 128)||(keySize == 192)||(keySize == 256)) {
            try {
                byte[] data = passphrase.getBytes("UTF8");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keySize/8);
                sKey = new SecretKeySpec(key, "AES");
            } catch (Exception ex) {
                System.err.println("Error generant la clau:" + ex);
            }
        }
        return sKey;
    }



    private Claus(){sKey = keygenKeyGeneration(256);}
    private Claus(String passphrase){sKey = passwordKeyGeneration(passphrase,256);}

    public static Claus getInstance(){
        if (claus == null) claus = new Claus();
        return claus;
    }

    public void setClau(String passphrase, int keySize){
        Claus.getInstance().sKey = Claus.getInstance().passwordKeyGeneration(passphrase, keySize);
    }

    public void setClau(int keySize){
        Claus.getInstance().sKey = Claus.getInstance().keygenKeyGeneration(keySize);
    }
}
