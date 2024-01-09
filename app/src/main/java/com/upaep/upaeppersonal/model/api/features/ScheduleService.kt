package com.upaep.upaeppersonal.model.api.features

import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleService @Inject constructor(
    private val api: ColaboradoresInterface,
    private val exceptionHandler: ExceptionHandler,
    private val serviceInterceptor: ServiceInterceptor
) {
    private val gson = Gson()

    suspend fun getSchedule(keyChain: IDDKeychain): UpaepStandardResponse<Any> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {

                UpaepStandardResponse()
            } catch (e: Exception) {
                UpaepStandardResponse()
            }
        }
    }
}