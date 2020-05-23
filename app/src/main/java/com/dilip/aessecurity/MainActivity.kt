package com.dilip.aessecurity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dilip.aes_library.AES
import com.dilip.aes_library.AESJava

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //String to encrypt using java
        Log.e(""+":From Java Encrypted: ",AESJava.encrypt("Dilip"))

        //String to encrypt in Kotlin
        val aes : AES = AES()
        val encrypt = aes.encrypt("Dilip")
        Log.e(""+":From Kotlin Encrypted:",encrypt)

        //decrypt in Kotlin
        val dncrypt = aes.decrypt(encrypt)
        Log.e(""+":From Kotlin Decrypted:",dncrypt)
    }
}
