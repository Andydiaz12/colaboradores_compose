package com.upaep.upaeppersonal.model.base

import com.upaep.upaeppersonal.model.base.entities.Test
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ColaboradoresInterface {
    @GET
    suspend fun getTest(@Url url: String = "https://mocki.io/v1/4f68e974-daee-4295-9c12-0de50dd8eb19") : Response<List<Test>>
}