package com.upaep.upaeppersonal.model.api.features

import android.util.Log
import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailCategories
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailItem
import com.upaep.upaeppersonal.model.entities.features.dailymail.MailOfDayRequestStructure
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DailyMailService @Inject constructor(
    private val exceptionHandler: ExceptionHandler,
    private val serviceInterceptor: ServiceInterceptor,
    private val api: ColaboradoresInterface
) {

    private val gson = Gson()

    suspend fun getMailCategories(keyChain: IDDKeychain): UpaepStandardResponse<List<DailyMailCategories>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMailCategories()
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val categories =
                        gson.fromJson(decryptedData, Array<DailyMailCategories>::class.java)
                            .toList()
                    UpaepStandardResponse(error = false, data = categories)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }

    suspend fun getMailByCategory(
        keyChain: IDDKeychain,
        request: MailOfDayRequestStructure
    ): UpaepStandardResponse<List<DailyMailItem>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val cryptedData = AESHelper.encrypt(gson.toJson(request), keyChain.AESKeychain!!)
                val base64 = Base64Helper.getBase64(cryptedData)
                val response = api.getDailyMailItems(cryptdata = base64)
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val items =
                        gson.fromJson(decryptedData, Array<DailyMailItem>::class.java).toList()
                    UpaepStandardResponse(data = items, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }

    suspend fun getItemDescription(
        keyChain: IDDKeychain,
        item: DailyMailItem
    ): UpaepStandardResponse<List<DailyMailItem>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val cryptedData = AESHelper.encrypt(gson.toJson(item), keyChain.AESKeychain!!)
                val base64 = Base64Helper.getBase64(cryptedData)
                val response = api.getDailyMailItems(cryptdata = base64)
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val items = gson.fromJson(decryptedData, Array<DailyMailItem>::class.java).toList()
                    UpaepStandardResponse(error = false, data = items)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }
}