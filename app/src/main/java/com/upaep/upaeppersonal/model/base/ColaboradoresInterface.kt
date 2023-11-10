package com.upaep.upaeppersonal.model.base

import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxFormResponse
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxSurveyType
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardRequest
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ColaboradoresInterface {

    @POST("general/keychain/")
    suspend fun getLockSmith(@Body cryptdata: UpaepStandardRequest): Response<UpaepStandardResponse<String>>

    @GET
    suspend fun getMailboxSurveys(@Url url: String = "https://mocki.io/v1/aa62bf88-5e9c-4ba9-a767-c9529fd67574"): Response<List<MailboxSurveyType>>

    @GET
    suspend fun testSurvey_1(@Url url: String = "https://mocki.io/v1/44909902-80d0-4d0e-b94d-61774c1975b5"): Response<List<MailboxFormResponse>>

    @GET
    suspend fun testSurvey_2(@Url url: String = "https://mocki.io/v1/0b280329-73f0-4a70-97a7-ae2773a3e7f4"): Response<List<MailboxFormResponse>>

    @GET
    suspend fun testSurvey_5(@Url url: String = "https://mocki.io/v1/18fee4f1-0c5a-4d2b-89e3-d5b118f3ed6a"): Response<List<MailboxFormResponse>>

    @GET
    suspend fun testSurvey_6(@Url url: String = "https://mocki.io/v1/820a7394-22a3-4e7a-b33e-d2a9a2b6aa86"): Response<List<MailboxFormResponse>>

    @GET
    suspend fun testSurvey_8(@Url url: String = "https://mocki.io/v1/19ac2b6a-53e4-4367-b550-7deeb1f10bb1"): Response<List<MailboxFormResponse>>

    @GET
    suspend fun testUpressNews(@Url url: String = "https://mocki.io/v1/47b26e2e-4ee1-49c7-af6d-054485a8378d"): Response<List<UpressItem>>

    @GET
    suspend fun getPrivacyPolicy()
}