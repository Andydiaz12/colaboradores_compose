package com.upaep.upaeppersonal.model.base

import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardRequest
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ColaboradoresInterface {

    @POST("general/keychain/")
    suspend fun getLockSmith(@Body cryptdata: UpaepStandardRequest): Response<UpaepStandardResponse<String>>

    @GET("general/privacypolicy/")
    suspend fun getPrivacyPolicy(): Response<UpaepStandardResponse<String>>

    @GET("general/mobile/benefits/categories/")
    suspend fun getBenefits(): Response<UpaepStandardResponse<String>>

    @GET("general/benefits_p/self/")
    suspend fun getBenefitByCategory(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("collaborators/pfi_p/categories/")
    suspend fun getPfiCategories(): Response<UpaepStandardResponse<String>>

    @GET("collaborators/pfi_p/courses/")
    suspend fun getPfiByCategory(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("general/directory_p/")
    suspend fun getFilteredDirectory(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("general/mailbox_p/topics/")
    suspend fun getMailboxTopics(): Response<UpaepStandardResponse<String>>

    @GET("general/mailbox_p/topics/forms/")
    suspend fun getMailboxForm(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("general/events_p/staff/")
    suspend fun getEvents(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("general/library_p/borrowedbooks/")
    suspend fun getBorrowedBooks(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("general/library_p/borrowedbooks/")
    suspend fun renewBorrowedBook(@Body cryptdata: UpaepStandardRequest): Response<UpaepStandardResponse<String>>

    @GET("general/upress_p/relevant/")
    suspend fun getNews(): Response<UpaepStandardResponse<String>>

    @GET("general/general/menu/")
    suspend fun getHomeFeatures(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("general/mobile/upress/categories/")
    suspend fun getUpressCategories(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("general/upress_p/news/")
    suspend fun getNewsByCategory(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("collaborators/mailofday_p/categories/")
    suspend fun getMailCategories(): Response<UpaepStandardResponse<String>>

    @GET("collaborators/mailofday_p/")
    suspend fun getDailyMailItems(@Query("CRYPTDATA") cryptdata: String): Response<UpaepStandardResponse<String>>

    @GET("collaborators/schedule_p/")
    suspend fun getSchedule(@Query("CRYPTDATA") cryptdata: String) : Response<UpaepStandardResponse<String>>
}