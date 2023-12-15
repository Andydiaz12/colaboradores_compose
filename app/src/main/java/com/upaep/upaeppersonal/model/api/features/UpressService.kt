package com.upaep.upaeppersonal.model.api.features

import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.jwt.JwtHelper
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.features.upress.UpressCategoryRequest
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.model.entities.features.upress.UpressOptions
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpressService @Inject constructor(
    private val serviceInterceptor: ServiceInterceptor,
    private val exceptionHandler: ExceptionHandler,
    private val api: ColaboradoresInterface
) {

    private val gson = Gson()

    suspend fun getUpressContent(
        keyChain: IDDKeychain,
        categoryId: UpressCategoryRequest
    ): UpaepStandardResponse<List<UpressItem>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val cryptedData = AESHelper.encrypt(gson.toJson(categoryId), keyChain.AESKeychain!!)
                val base64 = Base64Helper.getBase64(cryptedData)
                val response = api.getNewsByCategory(cryptdata = base64)
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptedData = AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val content = gson.fromJson(decryptedData, Array<UpressItem>::class.java).toList()
                    UpaepStandardResponse(data = content, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }

    suspend fun getUpressCategories(keyChain: IDDKeychain): UpaepStandardResponse<List<UpressOptions>> {
        return withContext(Dispatchers.IO) {
            try {
                val cryptedData = JwtHelper().createJwt(
                    API_KEY = keyChain.JWTKeychain!!.apiKey!!,
                    JWT_KEY = keyChain.JWTKeychain!!.jwtKey!!,
                    JSON_KEY = "key"
                )
                val response = api.getUpressCategories(cryptdata = cryptedData)
                if (response.isSuccessful && !response.body()!!.error) {
                    val cryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val decryptedData =
                        gson.fromJson(cryptedData, Array<UpressOptions>::class.java).toList()
                    UpaepStandardResponse(data = decryptedData, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }
}