package com.upaep.upaeppersonal.model.api.features

import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.features.directory.DirectoryFilter
import com.upaep.upaeppersonal.model.entities.features.directory.DirectoryPerson
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DirectoryService @Inject constructor(
    private val serviceInterceptor: ServiceInterceptor,
    private val api: ColaboradoresInterface,
    private val exceptionHandler: ExceptionHandler
) {

    private val gson = Gson()

    suspend fun getFilteredData(
        keyChain: IDDKeychain,
        filter: DirectoryFilter
    ): UpaepStandardResponse<List<DirectoryPerson>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val cryptedData = AESHelper.encrypt(gson.toJson(filter), keyChain.AESKeychain!!)
                val cryptdata = Base64Helper.getBase64(cryptedData)
                val response = api.getFilteredDirectory(cryptdata = cryptdata)
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptData = AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val listPeople = gson.fromJson(decryptData, Array<DirectoryPerson>::class.java).toList()
                    UpaepStandardResponse(data = listPeople, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }
}