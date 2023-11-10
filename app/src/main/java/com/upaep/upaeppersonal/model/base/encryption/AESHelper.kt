package com.upaep.upaeppersonal.model.base.encryption

import android.text.TextUtils
import android.util.Base64
import android.util.Log
import com.upaep.upaeppersonal.model.entities.features.locksmith.AESKeychain
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AESHelper {
    @JvmStatic fun encrypt(v:String, keychain: AESKeychain) = AES256.encrypt(v, keychain)
    @JvmStatic fun decrypt(v:String, keychain: AESKeychain) = AES256.decrypt(v, keychain)
}

private object AES256{
    private fun cipher(opmode: Int, aesKeychain: AESKeychain): Cipher {
        var AESkey = ""
        if(aesKeychain.key == null){
            AESkey = aesKeychain.inputKey!!
        }else{
            AESkey = aesKeychain.key!!
        }

        if(AESkey.length < 32)
            throw Exception ("SecretKey length is not 32 chars")
        var key =""

        if(AESkey.length > 32)
            key  = TextUtils.substring(AESkey,0,32)
        else
            key= AESkey


        val c = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val sk = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
        val iv = IvParameterSpec(aesKeychain.iv!!.toByteArray(Charsets.UTF_8))//IvParameterSpec(secretKey.substring(0, 16).toByteArray(Charsets.UTF_8))
        c.init(opmode, sk, iv)
        return c
    }
    fun encrypt(str:String, aesKeychain: AESKeychain ):String{
        val encrypted = cipher(Cipher.ENCRYPT_MODE, aesKeychain).doFinal(str.toByteArray(Charsets.UTF_8))
        return String( Base64.encode(encrypted, Base64.DEFAULT))
    }
    fun decrypt(str:String, aesKeychain: AESKeychain):String{
        Log.i("AES256", "decrypting: " + str)
        val byteStr = Base64.decode(str.toByteArray(Charsets.UTF_8), Base64.DEFAULT)

        return String(cipher(Cipher.DECRYPT_MODE, aesKeychain ).doFinal(byteStr))
    }

}