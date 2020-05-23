package com.dilip.aes_library

import android.annotation.SuppressLint
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.security.*
import java.security.cert.CertificateException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

class AES {

    private val TAG = AES::class.java.name
    private val AES_ALGORITHM = "AES"
    private val AES_TRANSFORMATION = "AES/ECB/PKCS5Padding"


    private var secretKey: SecretKeySpec? = null
    private lateinit var key: ByteArray

    private fun setKey(myKey: String) {
        var sha: MessageDigest? = null
        try {
            key = myKey.toByteArray(charset("UTF-8"))
            sha = MessageDigest.getInstance("SHA-256")
            key = sha.digest(key)
            key = Arrays.copyOf(key, 16)
            secretKey = SecretKeySpec(key, AES_ALGORITHM)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    /*This method is used for the encrypt data*/
    fun encrypt(strToEncrypt: String): String? {
        try {
            setKey("alias")
            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val inputBytes = cipher.doFinal(strToEncrypt.toByteArray())
            return Base64.encodeToString(inputBytes, Base64.DEFAULT)
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, Log.getStackTraceString(e))
        } catch (e: InvalidKeyException) {
            Log.e(TAG, Log.getStackTraceString(e))
        } catch (e: NoSuchPaddingException) {
            Log.e(TAG, Log.getStackTraceString(e))
        } catch (e: BadPaddingException) {
            Log.e(TAG, Log.getStackTraceString(e))
        } catch (e: IllegalBlockSizeException) {
            Log.e(TAG, Log.getStackTraceString(e))
        }
        return null
    }

    /*This method is used for the decrypt data*/
    fun decrypt(strToDecrypt: String?): String? {
        try {
            if (strToDecrypt == null) {
                return null
            }
            setKey("alias")
            val cipher = Cipher.getInstance(AES_TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            val inputBytes = cipher.doFinal(
                Base64.decode(
                    strToDecrypt,
                    Base64.DEFAULT
                )
            )
            return String(inputBytes)
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, Log.getStackTraceString(e))
        } catch (e: InvalidKeyException) {
            Log.e(TAG, Log.getStackTraceString(e))
        } catch (e: NoSuchPaddingException) {
            Log.e(TAG, Log.getStackTraceString(e))
        } catch (e: BadPaddingException) {
            Log.e(TAG, Log.getStackTraceString(e))
        } catch (e: IllegalBlockSizeException) {
            Log.e(TAG, Log.getStackTraceString(e))
        }
        return null
    }

    /*This method returns time in String format with pattern yyyy-MM-dd HH:mm:ss*/
    fun getFormatTime(): String? {
        @SuppressLint("SimpleDateFormat") val dateFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return dateFormat.format(Date(System.currentTimeMillis()))
    }

    @Throws(
        NoSuchProviderException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        KeyStoreException::class,
        CertificateException::class,
        IOException::class
    )
    fun generateYek() {
        /*
         * Generate a new EC key pair entry in the Android Keystore by
         * using the KeyPairGenerator API. The private key can only be
         * used for signing or verification and only with SHA-256 or
         * SHA-512 as the message digest.
         */
        val kpg = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore"
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            kpg.initialize(
                KeyGenParameterSpec.Builder(
                    "alias",
                    KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
                )
                    .setDigests(
                        KeyProperties.DIGEST_SHA256,
                        KeyProperties.DIGEST_SHA512
                    )
                    .build()
            )
        } else {
            //other key
        }
        val kp = kpg.generateKeyPair()
        kp.private

        /*
         * Load the Android KeyStore instance using the
         * "AndroidKeyStore" provider to list out what entries are
         * currently stored.
         */
        val ks = KeyStore.getInstance("AndroidKeyStore")
        ks.load(null)
        val aliases = ks.aliases()
    }
}