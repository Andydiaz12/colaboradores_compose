package com.upaep.upaeppersonal.model.api.features

import android.util.Log
import com.google.gson.Gson
import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.base.encryption.AESHelper
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.base.exceptions.ExceptionHandler
import com.upaep.upaeppersonal.model.base.retrofit.ServiceInterceptor
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxFormGet
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxFormResponse
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxSurveyType
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MailboxService @Inject constructor(
    private val serviceInterceptor: ServiceInterceptor,
    private val api: ColaboradoresInterface,
    private val exceptionHandler: ExceptionHandler
) {

    private val gson = Gson()

    suspend fun getMailboxOptions(keyChain: IDDKeychain): UpaepStandardResponse<List<MailboxSurveyType>> {
        serviceInterceptor.setAuthorization(keyChain)
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMailboxTopics()
                if (response.isSuccessful && !response.body()!!.error) {
                    val cryptdata =
                        AESHelper.decrypt(response.body()!!.data!!, keyChain.AESKeychain!!)
                    val surveys =
                        gson.fromJson(cryptdata, Array<MailboxSurveyType>::class.java).toList()
                    UpaepStandardResponse(data = surveys, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }

    suspend fun getMailboxSurvey(
        iddKeychain: IDDKeychain,
        mailboxFormGet: MailboxFormGet
    ): UpaepStandardResponse<List<MailboxFormResponse>> {
        serviceInterceptor.setAuthorization(iddKeychain)
        return withContext(Dispatchers.IO) {
            try {
                val cryptedData =
                    AESHelper.encrypt(gson.toJson(mailboxFormGet), iddKeychain.AESKeychain!!)
                val cryptdata = Base64Helper.getBase64(cryptedData)
                val response = api.getMailboxForm(cryptdata = cryptdata)
                if (response.isSuccessful && !response.body()!!.error) {
                    val mailboxResponse =
                        AESHelper.decrypt(response.body()!!.data!!, iddKeychain.AESKeychain!!)
                    val surveys =
                        gson.fromJson(mailboxResponse, Array<MailboxFormResponse>::class.java)
                            .toList()
                    UpaepStandardResponse(data = surveys, error = false)
                } else {
                    UpaepStandardResponse()
                }
            } catch (e: Exception) {
                UpaepStandardResponse(message = exceptionHandler(e))
            }
        }
    }
}