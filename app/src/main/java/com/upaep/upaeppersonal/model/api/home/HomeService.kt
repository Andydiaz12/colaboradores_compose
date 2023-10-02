package com.upaep.upaeppersonal.model.api.home

import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeService @Inject constructor(private val api: ColaboradoresInterface) {
//    suspend fun getTest() : UpaepStandardResponse<List<Test>> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = api.getTest()
//                if(response.isSuccessful) {
//                    UpaepStandardResponse(data = response.body()!!, error = false)
//                } else {
//                    UpaepStandardResponse(data = emptyList())
//                }
//            } catch (e: Exception) {
//                UpaepStandardResponse(data = emptyList())
//            }
//        }
//    }
}