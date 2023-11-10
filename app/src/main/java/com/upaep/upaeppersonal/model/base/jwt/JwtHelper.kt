package com.upaep.upaeppersonal.model.base.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import javax.inject.Inject

class JwtHelper @Inject constructor() {
    fun createJwt(API_KEY: String, JWT_KEY: String, JSON_KEY: String = "key"): String {
        val now = Date()
        return Jwts.builder()
            .claim(JSON_KEY, API_KEY)
            .setIssuedAt(now)
            .signWith(SignatureAlgorithm.HS256, JWT_KEY.toByteArray())
            .compact()
    }
}