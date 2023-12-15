package com.upaep.upaeppersonal.model.api.features

import android.util.Log
import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiByCategory
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiCategories
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiCategoryResponse
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PfiService @Inject constructor(
    private val api: ColaboradoresInterface,
    private val serviceInterceptor: ServiceInterceptor,
    private val exceptionHandler: ExceptionHandler
) {
    private val gson = Gson()
    suspend fun getPfiCategories(keyChain: IDDKeychain): UpaepStandardResponse<List<PfiCategories>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getPfiCategories()
                if (response.isSuccessful && !response.body()!!.error) {
                    val cryptdata = response.body()!!.data!!
                    val decryptdata = AESHelper.decrypt(cryptdata, keyChain.AESKeychain!!)
                    val listCategories =
                        gson.fromJson(decryptdata, Array<PfiCategories>::class.java).toList()
                    UpaepStandardResponse(error = false, data = listCategories)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }

    suspend fun getPfiByCategory(
        keyChain: IDDKeychain,
        categoryId: PfiByCategory
    ): UpaepStandardResponse<List<PfiCategoryResponse>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val decryptedData = gson.toJson(categoryId)
                val cryptdata =
                    Base64Helper.getBase64(AESHelper.encrypt(decryptedData, keyChain.AESKeychain!!))
                val response = api.getPfiByCategory(cryptdata = cryptdata)
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val categories =
                        gson.fromJson(decryptedData, Array<PfiCategoryResponse>::class.java)
                            .toList()
                    UpaepStandardResponse(data = categories, error = response.body()!!.error)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse()
            }
        }
    }
}