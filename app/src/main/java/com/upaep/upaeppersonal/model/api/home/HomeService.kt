package com.upaep.upaeppersonal.model.api.home

import android.util.Log
import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.features.home.Feature
import com.upaep.upaeppersonal.model.entities.features.home.FeaturesRequest
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeService @Inject constructor(
    private val api: ColaboradoresInterface,
    private val serviceInterceptor: ServiceInterceptor,
    private val exceptionHandler: ExceptionHandler
) {

    private val gson = Gson()

//    suspend fun getTest() : UpaepStandardResponse<List<Test>> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = api.getTest()
//                if(response.isSuccessful) {
//                    UpaepStandardResponse(data = response.body()!!, error = false)
//                } else {
//                    UpaepStandardResponse(data = emptyList())
//                }
//            } catch (e: Exception) {
//                UpaepStandardResponse(data = emptyList())
//            }
//        }
//    }

    suspend fun getFeatures(
        keyChain: IDDKeychain,
        featuresRequestFeatures: FeaturesRequest
    ): UpaepStandardResponse<List<Feature>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val json = gson.toJson(featuresRequestFeatures)
                val base64 = Base64Helper.getBase64(AESHelper.encrypt(json, keyChain.AESKeychain!!))
                val response = api.getHomeFeatures(cryptdata = base64)
                Log.i("homeVMDebug_features", response.body().toString())
                Log.i("homeVMDebug_features", response.toString())
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val features = gson.fromJson(decryptedData, Array<Feature>::class.java).toList()
                    UpaepStandardResponse(data = features, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }

    suspend fun getNews(keyChain: IDDKeychain): UpaepStandardResponse<List<UpressItem>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getNews()
                Log.i("homeVMDebug_news", response.body().toString())
                Log.i("homeVMDebug_news", response.toString())
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val news = gson.fromJson(decryptedData, Array<UpressItem>::class.java).toList()
                    UpaepStandardResponse(data = news, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }
}