package com.upaep.upaeppersonal.model.api.features

import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.base.PeopleId
import com.upaep.upaeppersonal.model.entities.features.library.BorrowedBook
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LibraryService @Inject constructor(
    private val api: ColaboradoresInterface,
    private val exceptionHandler: ExceptionHandler,
    private val serviceInterceptor: ServiceInterceptor,
) {

    private val gson = Gson()

    suspend fun getBorrowedBooks(
        keyChain: IDDKeychain,
        peopleId: PeopleId
    ): UpaepStandardResponse<List<BorrowedBook>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val crypteddata = AESHelper.encrypt(gson.toJson(peopleId), keyChain.AESKeychain!!)
                val cryptdata = Base64Helper.getBase64(crypteddata)
                val response = api.getBorrowedBooks(cryptdata = cryptdata)
                if (response.isSuccessful && !response.body()!!.error) {
                    val decryptedData =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val books =
                        gson.fromJson(decryptedData, Array<BorrowedBook>::class.java).toList()
                    UpaepStandardResponse(data = books, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }
}