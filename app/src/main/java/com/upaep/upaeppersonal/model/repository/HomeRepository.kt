package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.home.HomeService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.entities.features.home.Feature
import com.upaep.upaeppersonal.model.entities.features.home.FeaturesRequest
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val homeService: HomeService,
    private val locksmithHelper: LocksmithHelper
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

    suspend fun getFeatures(features: FeaturesRequest): UpaepStandardResponse<List<Feature>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        val features = homeService.getFeatures(keyChain = locksmith.data!!.apiGetMenu!!, featuresRequestFeatures = features)
        // TODO: Implementación de almacenado en DAO
        return features.copy()
    }

    suspend fun getNews(): UpaepStandardResponse<List<UpressItem>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        val news = homeService.getNews(keyChain = locksmith.data!!.generalMovilKeychain!!)
        if (!news.error) { //Agregar DAO

        }
        return withContext(Dispatchers.IO) { news.copy() } //Cambiar el data por el del DAO
    }
}