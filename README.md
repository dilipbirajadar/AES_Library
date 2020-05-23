# AES_Library
This library does encryption and decryption using AES SHA-256 algorithm you just need to pass string for encryption or decryption with AESJava.java class  or AES.kt class. This Library support both Java and Kotlin langauge. 

# Overview

The AES Library does the following:

This library does encryption and decryption just need to pass string with AESJava class if you want to Java langauge otherwise
you can use kotline with AES.kt

# Integration of AES Library

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.dilipbirajadar:AES_Library:1.0.0'
	}
 
# Java
- Implimentation in Java for encryption : AESJava.encrypt(stringToencrypt) 
- Implimentation in Java for decryption : AESJava.decrypt(stringTodecrypt) 

e.g 
 - String to encrypt using java
 
        Log.e(""+":From Java Encrypted: ",AESJava.encrypt("Dilip"))

        

# Kotlin
- Implimentation in Kotlin for Encryption and Decryption 

- String to encrypt in Kotlin

        val aes : AES = AES()
        val encrypt = aes.encrypt("Dilip")
        Log.e(""+":From Kotlin Encrypted:",encrypt)
        
 - String to decrypt in Kotlin
 
        val dncrypt = aes.decrypt(encrypt)
        Log.e(""+":From Kotlin Decrypted:",dncrypt)       

# To achieve this, 
 - Designed AESJava
 - Designed AES.kt
 - Design the AES-SHA-256 Alogrithm for Encryption and Decryption
 - Pass String if you want use java then AESJava.class or if you want to use kotline then AES.kt class  



# Used Security 

- AES SHA-256 Algorithm for encryption and decryption technique.

# Libraries
This app leverages android X.


# For Test App
Clone or Download from this repo.
Open downlaoded project in android studio and add to your project this dependancy and just run.
Pass which string you want to encrypt or decrypt.

# Dependancy

implementation 'com.github.dilipbirajadar:AES_Library:1.0.0'
