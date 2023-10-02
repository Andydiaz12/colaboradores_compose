package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.home.HomeService
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val homeService: HomeService
) {

//    val test: Flow<UpaepStandardResponse<List<Test>>> = initData()

//    private fun initData(): Flow<UpaepStandardResponse<List<Test>>>  = flow {
//        val response = homeService.getTest()
//        testDAO.deleteData()
//        testDAO.addData(response.data)
//        emitAll(testDAO.getTest().map { response.copy(data = it) })
//    }
//
//    suspend fun addTest(test: Test) {
//        testDAO.addData(listOf(test))
//    }
//
//    suspend fun deleteData() {
//        testDAO.deleteData()
//    }
}