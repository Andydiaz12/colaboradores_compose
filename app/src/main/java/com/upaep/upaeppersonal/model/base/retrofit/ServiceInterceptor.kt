package com.upaep.upaeppersonal.model.base.retrofit

class ServiceInterceptor {
    private var authorization: String = ""

//    fun setAuthorization(keyChain: IDDKeychain) {
//        this.authorization = JwtHelper().createJwt(
//            API_KEY = keyChain.JWTKeychain!!.apiKey!!,
//            JWT_KEY = keyChain.JWTKeychain!!.jwtKey!!,
//            JSON_KEY = keyChain.JWTKeychain?.jsonKey ?: "key"
//        )
//    }

//    fun setAuthOpenId(text: String) {
//        this.authorization = text
//    }
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request: Request = chain.request()
//        val requestBuilder: Request.Builder = request.newBuilder()
//        requestBuilder.addHeader("Authorization", authorization)
//        return chain.proceed(requestBuilder.build())
//    }
}