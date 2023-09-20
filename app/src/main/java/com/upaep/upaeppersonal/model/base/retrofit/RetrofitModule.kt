package com.upaep.upaeppersonal.model.base.retrofit

import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(
//        serviceInterceptor: ServiceInterceptor
    ): Retrofit {
        val interceptor: OkHttpClient =
            OkHttpClient
                .Builder()
//                .addInterceptor(serviceInterceptor)
                .build()
        return Retrofit.Builder()
            .baseUrl("https://mocki.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesApiClient(retrofit: Retrofit): ColaboradoresInterface {
        return retrofit.create(ColaboradoresInterface::class.java)
    }
}