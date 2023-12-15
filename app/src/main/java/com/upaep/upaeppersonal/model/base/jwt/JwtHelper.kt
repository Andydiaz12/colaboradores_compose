package com.upaep.upaeppersonal.model.base.jwt

import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychain
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import javax.inject.Inject

class JwtHelper @Inject constructor() {
    operator fun invoke(keyChain: IDDKeychain): String {
        val now = Date()
        return Jwts.builder()
            .claim(keyChain.JWTKeychain?.jsonKey ?: "key", keyChain.JWTKeychain!!.apiKey!!)
            .setIssuedAt(now)
            .signWith(SignatureAlgorithm.HS256, keyChain.JWTKeychain!!.jwtKey!!.toByteArray())
            .compact()
    }

    fun createJwt(API_KEY: String, JWT_KEY: String, JSON_KEY: String = "key"): String {
        val now = Date()
        return Jwts.builder()
            .claim(JSON_KEY, API_KEY)
            .setIssuedAt(now)
            .signWith(SignatureAlgorithm.HS256, JWT_KEY.toByteArray())
            .compact()
    }
}