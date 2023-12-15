package com.upaep.upaeppersonal.model.base.retrofit

import android.util.Log
import com.upaep.upaeppersonal.model.base.jwt.JwtHelper
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceInterceptor @Inject constructor() : Interceptor {
    private var authorization: String = ""

    fun setAuthorization(keyChain: IDDKeychain) {
        this.authorization = JwtHelper().createJwt(
            API_KEY = keyChain.JWTKeychain!!.apiKey!!,
            JWT_KEY = keyChain.JWTKeychain!!.jwtKey!!,
            JSON_KEY = keyChain.JWTKeychain?.jsonKey ?: "key"
        )
    }

    fun setAuthOpenId(text: String) {
        this.authorization = text
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val requestBuilder: Request.Builder = request.newBuilder()
        requestBuilder.addHeader("Authorization", authorization)
        return chain.proceed(requestBuilder.build())
    }
}