package com.upaep.upaeppersonal.model.api.features

import android.util.Log
import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.base.PeopleId
import com.upaep.upaeppersonal.model.entities.features.events.EventsResponse
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventsService @Inject constructor(
    private val api: ColaboradoresInterface,
    private val exceptionHandler: ExceptionHandler,
    private val serviceInterceptor: ServiceInterceptor
) {

    private val gson = Gson()

    suspend fun getEvents(
        keyChain: IDDKeychain,
        peopleId: PeopleId
    ): UpaepStandardResponse<List<EventsResponse>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val json = gson.toJson(peopleId)
                val base64 = Base64Helper.getBase64(AESHelper.encrypt(json, keyChain.AESKeychain!!))
                val response = api.getEvents(cryptdata = base64)
                if (response.isSuccessful && response.body()!!.error) {
                    val decryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val events =
                        gson.fromJson(decryptedData, Array<EventsResponse>::class.java).toList()
                    UpaepStandardResponse(data = events, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }
}