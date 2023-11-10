package com.upaep.upaeppersonal.model.api

import android.util.Log
import com.google.gson.Gson
import com.upaep.upaeppersonal.BuildConfig
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.aes.AESAncestral
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.features.locksmith.JWTKeychain
import com.upaep.upaeppersonal.model.entities.features.locksmith.Locksmith
import com.upaep.upaeppersonal.model.entities.features.locksmith.LocksmithStructure
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardRequest
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocksmithService @Inject constructor(
    private val serviceInterceptor: ServiceInterceptor,
    private val api: ColaboradoresInterface,
    private val exceptionHandler: ExceptionHandler
) {

    private val gson = Gson()

    suspend fun getLocksmith(): UpaepStandardResponse<Locksmith> {
        serviceInterceptor.setAuthorization(
            IDDKeychain(
                JWTKeychain = JWTKeychain(
                    apiKey = BuildConfig.LOCKSMITH_AES_KEY,
                    jwtKey = BuildConfig.LOCKSMITH_JWT_KEY,
                    jsonKey = "apiClient"
                )
            )
        )
        val jsonString =
            gson.toJson(LocksmithStructure(BuildConfig.LOCKSMITH_CONFIG))
        val jsonEncrypt = AESHelper.encrypt(jsonString, AESAncestral.getAES())
        val request = UpaepStandardRequest(CRYPTDATA = jsonEncrypt)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getLockSmith(cryptdata = request)
                if(response.isSuccessful && response.body()!!.error!!) {
                    UpaepStandardResponse()
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
//        api.getLockSmith()
    }
}