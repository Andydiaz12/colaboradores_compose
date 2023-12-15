package com.upaep.upaeppersonal.model.api.features

import android.util.Log
import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.features.benefits.Benefit
import com.upaep.upaeppersonal.model.entities.features.benefits.BenefitCategory
import com.upaep.upaeppersonal.model.entities.features.benefits.BenefitDetail
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BenefitsService @Inject constructor(
    private val api: ColaboradoresInterface,
    private val serviceInterceptor: ServiceInterceptor,
    private val exceptionHandler: ExceptionHandler
) {

    private val gson = Gson()

    suspend fun getBenefits(keyChain: IDDKeychain): UpaepStandardResponse<List<Benefit>> {
        serviceInterceptor.setAuthorization(keyChain = keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getBenefits()
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val benefits = gson.fromJson(decryptData, Array<Benefit>::class.java).toList()
                    UpaepStandardResponse(data = benefits, error = false)
                } else {
                    UpaepStandardResponse(message = exceptionHandler(NullPointerException()))
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }

    suspend fun getBenefitByCategory(
        keyChain: IDDKeychain,
        benefitCategory: BenefitCategory
    ): UpaepStandardResponse<List<BenefitDetail>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val cryptedData =
                    AESHelper.encrypt(gson.toJson(benefitCategory), keyChain.AESKeychain!!)
                val response = api.getBenefitByCategory(cryptdata = Base64Helper.getBase64(cryptedData))
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val benefits =
                        gson.fromJson(decryptedData, Array<BenefitDetail>::class.java).asList()
                    UpaepStandardResponse(
                        data = benefits,
                        error = response.body()!!.error,
                        message = response.body()!!.message,
                        statusCode = response.body()!!.statusCode
                    )
                } else {
                    Log.i("benefitsDebug_else", response.toString())
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                Log.i("benefitsDebug_e", e.toString())
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }
}