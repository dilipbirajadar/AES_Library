package com.dilip.aes_library;

import android.annotation.SuppressLint;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESJava {
    private static final String TAG = AESJava.class.getName();
    private static String AES_ALGORITHM = "AES";
    private static String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";


    private static SecretKeySpec secretKey;
    private static byte[] key;

    private static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, AES_ALGORITHM);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /*This method is used for the encrypt data*/
    public static String encrypt(String strToEncrypt ) {
        try {
            setKey("alias");
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] inputBytes = cipher.doFinal(strToEncrypt.getBytes());
            return Base64.encodeToString(inputBytes, Base64.DEFAULT);

        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            Log.e( TAG, Log.getStackTraceString( e));
        }

        return null;
    }
    /*This method is used for the decrypt data*/
    public static String decrypt(String strToDecrypt) {
        try {
            if(strToDecrypt == null) {
                return null;
            }
            setKey("alias");

            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] inputBytes = cipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT));
            return new String(inputBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            Log.e( TAG, Log.getStackTraceString( e));
        }

        return null;
    }

    /*This method returns time in String format with pattern yyyy-MM-dd HH:mm:ss*/
    public static String getFormatTime() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(System.currentTimeMillis()));
    }

    public static void generateYek() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, KeyStoreException, CertificateException, IOException {
        /*
         * Generate a new EC key pair entry in the Android Keystore by
         * using the KeyPairGenerator API. The private key can only be
         * used for signing or verification and only with SHA-256 or
         * SHA-512 as the message digest.
         */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            kpg.initialize(new KeyGenParameterSpec.Builder(
                    "alias",
                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                    .setDigests(KeyProperties.DIGEST_SHA256,
                            KeyProperties.DIGEST_SHA512)
                    .build());
        }else {
            //other key
        }

        KeyPair kp = kpg.generateKeyPair();
        kp.getPrivate();

        /*
         * Load the Android KeyStore instance using the
         * "AndroidKeyStore" provider to list out what entries are
         * currently stored.
         */
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);
        Enumeration<String> aliases = ks.aliases();
    }
}
