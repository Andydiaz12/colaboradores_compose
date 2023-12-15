package com.upaep.upaeppersonal.model.api.features

import android.security.KeyChain
import android.util.Log
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.jwt.JwtHelper
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.features.locksmith.AESKeychain
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MenuService @Inject constructor(
    private val api: ColaboradoresInterface,
    private val serviceInterceptor: ServiceInterceptor,
    private val exceptionHandler: ExceptionHandler
) {
    suspend fun getPrivacyPolicy(keyChain: IDDKeychain): UpaepStandardResponse<String> {
        serviceInterceptor.setAuthorization(keyChain = keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPrivacyPolicy()
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptdata =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    UpaepStandardResponse(data = decryptdata, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }
}